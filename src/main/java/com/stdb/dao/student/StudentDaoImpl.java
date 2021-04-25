package com.stdb.dao.student;

import com.stdb.entity.Student;
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
    public List<Student> getByGroup(String[] groups, Map<String, Object> filters) {
        StringBuilder groupsFilter = new StringBuilder("WHERE (");
        for(String group: groups) {
            groupsFilter.append("grp.name = ").append("\'").append(group).append("\'").append(" OR ");
        }
        groupsFilter.delete(groupsFilter.length() - 4, groupsFilter.length()-1);
        groupsFilter.append(") AND ");

        String sql= "SELECT std.id, std.name, std.id_group, std.id_faculty, std.stipendium, std.gender, std.age, std.kids " +
                "FROM students std INNER JOIN groups grp " +
                "ON std.id_group = grp.id " ;
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
        for(int course: courses){
            courseFilter.append(course).append(",");
        }
        courseFilter.deleteCharAt(courseFilter.length() - 1);
        courseFilter.append(") AND ");

        String sql= "SELECT std.id, std.name, std.id_group, std.id_faculty, std.stipendium, std.gender, std.age, std.kids " +
                "FROM students std INNER JOIN groups grp " +
                "ON std.id_group = grp.id " ;
        sql += courseFilter;
        sql += StudentFilterBuilder.buildFilter(filters);

        return jdbcTemplate.query(
                sql,
                new StudentRowMapper()
        );
    }
}
