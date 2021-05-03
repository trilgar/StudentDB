package com.stdb.dao.student;

import com.stdb.entity.Student;
import com.stdb.helpers.IntervalFilter;
import com.stdb.service.filterbuilder.StudentFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDaoImpl implements StudentDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student createStudent(Student student) {
        String sql = "INSERT INTO students (name,id_group, id_faculty,stipendium,gender,age,kids) " +
                "VALUES (?,?,?,?,?::gender_enum, ?,?)";
        jdbcTemplate.update(
                sql,
                student.getName(),
                student.getIdGroup(),
                student.getIdFaculty(),
                student.getStipendium(),
                student.getGender().toString(),
                student.getAge(),
                student.getKids()
        );
        return this.getByName(student.getName());
    }

    @Override
    public Student editStudent(Student student, int studentId) {
        String sql = "UPDATE students SET name = ?,id_group = ?,id_faculty = ?,stipendium = ?, " +
                "gender = ?::gender_enum, age = ?, kids = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                student.getName(),
                student.getIdGroup(),
                student.getIdFaculty(),
                student.getStipendium(),
                student.getGender().toString(),
                student.getAge(),
                student.getKids(),
                studentId
        );
        return this.getByName(student.getName());
    }

    @Override
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE id = ?";
        jdbcTemplate.update(sql, studentId);
    }

    @Override
    public Student getById(int studentId) {
        String sql = "SELECT id, name, id_group, id_faculty, stipendium, gender, age, kids " +
                "FROM students WHERE id = ?";
        List<Student> students = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, studentId),
                new StudentRowMapper()
        );
        return students.size() == 1 ? students.get(0) : null;
    }

    @Override
    public Student getByName(String name) {
        String sql = "SELECT id, name, id_group, id_faculty, stipendium, gender, age, kids " +
                "FROM students WHERE name = ?";
        List<Student> students = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new StudentRowMapper()
        );
        return students.size() == 1 ? students.get(0) : null;
    }


    @Override
    public List<Student> getByContainingName(String name) {
        String sql = "SELECT id, name, id_group, id_faculty, stipendium, gender, age, kids " +
                "FROM students WHERE name LIKE concat('%', ?, '%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByGroup(String[] groups, Map<String, Object> filters) {
        StringBuilder groupsFilter = new StringBuilder("WHERE (");
        for (String group : groups) {
            groupsFilter.append("grp.name = ").append("'").append(group).append("'").append(" OR ");
        }
        groupsFilter.delete(groupsFilter.length() - 4, groupsFilter.length() - 1);
        groupsFilter.append(")");
        if(!filters.isEmpty()){
            groupsFilter.append(" AND ");
        }

        String sql = "SELECT DISTINCT std.id, std.name, std.id_group, std.id_faculty, std.stipendium, std.gender, std.age, std.kids " +
                "FROM students std INNER JOIN groups grp " +
                "ON std.id_group = grp.id ";
        sql += groupsFilter;
        sql += StudentFilterBuilder.buildFilter(filters);

        return jdbcTemplate.query(
                sql,
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByCourse(Integer[] courses, Map<String, Object> filters) {
        StringBuilder courseFilter = new StringBuilder("WHERE grp.course IN(");
        for (int course : courses) {
            courseFilter.append(course).append(",");
        }
        courseFilter.deleteCharAt(courseFilter.length() - 1);
        courseFilter.append(") AND ");

        String sql = "SELECT DISTINCT std.id, std.name, std.id_group, std.id_faculty, std.stipendium, std.gender, std.age, std.kids " +
                "FROM students std INNER JOIN groups grp " +
                "ON std.id_group = grp.id ";
        sql += courseFilter;
        sql += StudentFilterBuilder.buildFilter(filters);

        return jdbcTemplate.query(
                sql,
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByDisciplineAndMark(List<Integer> groupIds, int idDiscipline, int mark) {
        String sql = "SELECT DISTINCT s.id, name, id_group, id_faculty, stipendium, gender, age, kids " +
                "FROM students s INNER JOIN exam e on s.id = e.id_student ";
        sql += StudentFilterBuilder.getFilterByGroup(groupIds);
        sql += " AND e.id_discipline =? AND e.mark = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, idDiscipline);
                    preparedStatement.setInt(2, mark);
                },
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByGroupAndMarks(List<Integer> groupIds, int idFaculty, int minMark, int semester) {
        String sql = "SELECT DISTINCT s.id,s.name,s.id_group,id_faculty,stipendium,gender,age,kids " +
                "FROM (" +
                "         select id_student id_s, min(mark) as m" +
                "         FROM exam" +
                "         GROUP BY id_student " +
                "         HAVING max(exam.mark) >= ? " +
                "     ) max_mark " +
                "INNER JOIN students s on max_mark.id_s = s.id " +
                "INNER JOIN exam e on s.id = e.id_student " +
                "INNER JOIN discipline d on d.id = e.id_discipline ";

        sql += StudentFilterBuilder.getFilterByGroup(groupIds);
        sql += " AND s.id_faculty = ? AND d.semester = ? ";


        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, minMark);
                    preparedStatement.setInt(2, idFaculty);
                    preparedStatement.setInt(3, semester);
                },
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByCourseAndMarks(int course, int idFaculty, int minMark, int semester) {
        String sql = "SELECT DISTINCT s.id,s.name,s.id_group,id_faculty,stipendium,gender,age,kids " +
                "FROM (" +
                "         select id_student id_s, min(mark) as m" +
                "         FROM exam" +
                "         GROUP BY id_student " +
                "         HAVING max(exam.mark) >= ? " +
                "     ) max_mark " +
                "INNER JOIN students s on max_mark.id_s = s.id " +
                "INNER JOIN groups g on s.id_group = g.id " +
                "INNER JOIN discipline d on g.id = d.id_group " +
                "WHERE g.course = ? AND s.id_faculty = ? AND d.semester = ?";

        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, minMark);
                    preparedStatement.setInt(2, course);
                    preparedStatement.setInt(3, idFaculty);
                    preparedStatement.setInt(4, semester);
                },
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByGroupAndSemester(List<Integer> groupIds, IntervalFilter semester) {
        String sql = "SELECT DISTINCT s.id, s.name, s.id_group, id_faculty, stipendium, gender, age, kids " +
                "FROM students s " +
                "INNER JOIN groups g on s.id_group = g.id " +
                "INNER JOIN discipline d on g.id = d.id_group ";
        sql += StudentFilterBuilder.getFilterByGroup(groupIds);
        sql += " AND semester BETWEEN ? AND ?";

        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, semester.getFrom());
                    preparedStatement.setInt(2, semester.getTo());
                },
                new StudentRowMapper()
        );
    }

    @Override
    public List<Student> getByMarkAndSemester(int mark, int idDiscipline, IntervalFilter semester) {
        String sql = "SELECT DISTINCT s.id, s.name, s.id_group, s.id_faculty, stipendium, gender, age, kids " +
                "FROM students s " +
                "INNER JOIN exam e on s.id = e.id_student " +
                "INNER JOIN discipline d on d.id = e.id_discipline " +
                "WHERE e.mark = ? AND e.id_discipline = ? AND d.semester BETWEEN ? AND ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, mark);
                    preparedStatement.setInt(2, idDiscipline);
                    preparedStatement.setInt(3, semester.getFrom());
                    preparedStatement.setInt(4, semester.getTo());
                },
                new StudentRowMapper()
        );
    }

}
