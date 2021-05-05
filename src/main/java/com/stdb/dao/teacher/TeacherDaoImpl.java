package com.stdb.dao.teacher;

import com.stdb.entity.Teacher;
import com.stdb.entity.TeacherCategory;
import com.stdb.helpers.IntervalFilter;
import com.stdb.service.filterbuilder.MainFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher create(Teacher teacher) {
        String sql = "INSERT INTO teachers (name,id_faculty, category, year,wage, is_asp,gender,age,kids, id_cathedra) " +
                "VALUES (?,?,?::teacher_category,?,?,?::asp_status,?::gender_enum, ?,?,?)";
        jdbcTemplate.update(
                sql,
                teacher.getName(),
                teacher.getFacultyId(),
                teacher.getCategory().toString(),
                teacher.getYear(),
                teacher.getWage(),
                teacher.getAsp().toString(),
                teacher.getGender().toString(),
                teacher.getAge(),
                teacher.getKids(),
                teacher.getIdCathedra()
        );
        return getByName(teacher.getName());
    }

    @Override
    public Teacher edit(Teacher teacher, int idTeacher) {
        String sql = "UPDATE teachers SET name = ?,id_faculty = ?, category = ?::teacher_category, year = ?,wage = ?," +
                " is_asp = ?::asp_status,gender = ?::gender_enum,age = ?,kids = ?, id_cathedra = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                teacher.getName(),
                teacher.getFacultyId(),
                teacher.getCategory().toString(),
                teacher.getYear(),
                teacher.getWage(),
                teacher.getAsp().toString(),
                teacher.getGender().toString(),
                teacher.getAge(),
                teacher.getKids(),
                teacher.getIdCathedra(),
                idTeacher
        );
        return getByName(teacher.getName());
    }

    @Override
    public void delete(int idTeacher) {
        String sql = "DELETE FROM teachers WHERE id = ?";
        jdbcTemplate.update(sql, idTeacher);
    }

    @Override
    public Teacher getByName(String name) {
        String sql = "SELECT tcr.id, tcr.name, tcr.id_faculty, tcr.category, tcr.year, tcr.wage, tcr.is_asp, tcr.gender, tcr.age, tcr.kids, tcr.id_cathedra " +
                "FROM teachers tcr WHERE name = ? ";
        List<Teacher> teachers = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new TeacherRowMapper()
        );
        return teachers.size() == 1 ? teachers.get(0) : null;
    }

    @Override
    public List<Teacher> getByContainName(String name) {
        String sql = "SELECT tcr.id, tcr.name, tcr.id_faculty, tcr.category, tcr.year, tcr.wage, tcr.is_asp, tcr.gender, tcr.age, tcr.kids, tcr.id_cathedra " +
                "FROM teachers tcr WHERE name Like concat('%', ?, '%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByFaculty(String[] faculties, Map<String, Object> filters) {
        StringBuilder facultiesFilter = new StringBuilder("WHERE (");
        for (String faculty : faculties) {
            facultiesFilter.append("fct.name = ").append("'").append(faculty).append("'").append(" OR ");
        }
        facultiesFilter.delete(facultiesFilter.length() - 4, facultiesFilter.length() - 1);
        facultiesFilter.append(") AND ");

        String sql = "SELECT Distinct tcr.id, tcr.name, tcr.id_faculty, tcr.category, tcr.year, tcr.wage, tcr.is_asp, tcr.gender, tcr.age, tcr.kids, tcr.id_cathedra " +
                "FROM teachers tcr INNER JOIN faculty fct on tcr.id_faculty = fct.id INNER JOIN science_work sw on tcr.id = sw.id_teacher ";

        sql += facultiesFilter + " ";
        sql += MainFilterBuilder.getTeacherFilter(filters);

        return jdbcTemplate.query(
                sql,
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByCathedra(String[] cathedras, Map<String, Object> filters) {
        StringBuilder cathedrasFilter = new StringBuilder("WHERE (");
        for (String cathedra : cathedras) {
            cathedrasFilter.append("cfd.name = ").append("'").append(cathedra).append("'").append(" OR ");
        }
        cathedrasFilter.delete(cathedrasFilter.length() - 4, cathedrasFilter.length() - 1);
        cathedrasFilter.append(") AND ");

        String sql = "SELECT DISTINCT tcr.id, tcr.name, tcr.id_faculty, tcr.category, tcr.year, tcr.wage, tcr.is_asp, tcr.gender, tcr.age, tcr.kids, tcr.id_cathedra " +
                "FROM teachers tcr INNER JOIN cathedra cfd on cfd.id = tcr.id_cathedra INNER JOIN science_work sw on tcr.id = sw.id_teacher ";

        sql += cathedrasFilter;
        sql += MainFilterBuilder.getTeacherFilter(filters);

        return jdbcTemplate.query(
                sql,
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByGroup(String dName, int idGroup, int idFaculty) {
        String sql = "SELECT DISTINCT teachers.id, teachers.name, id_faculty, category, year, wage, is_asp, gender, age, kids, id_cathedra  " +
                "FROM teachers INNER JOIN discipline d on teachers.id = d.id_teacher " +
                "WHERE d.name = ? AND d.id_group = ? AND teachers.id_faculty = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, dName);
                    preparedStatement.setInt(2, idGroup);
                    preparedStatement.setInt(3, idFaculty);
                },
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByCourse(String dName, int course, int idFaculty) {
        String sql = "SELECT DISTINCT teachers.id, teachers.name, id_faculty, category, year, wage, is_asp, gender, age, kids, id_cathedra  " +
                "FROM teachers INNER JOIN discipline d on teachers.id = d.id_teacher " +
                "WHERE d.name = ? AND d.course = ? AND teachers.id_faculty = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, dName);
                    preparedStatement.setInt(2, course);
                    preparedStatement.setInt(3, idFaculty);
                },
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByCategoryGroup(List<TeacherCategory> teacherCategories, int idGroup, int idFaculty, IntervalFilter semester) {
        String sql = "SELECT DISTINCT t.id, t.name, id_faculty, category, year, wage, is_asp, gender, age, kids, id_cathedra " +
                "FROM teachers t  INNER JOIN discipline d on t.id = d.id_teacher ";
        sql += MainFilterBuilder.getTeacherCategoryFilter(teacherCategories);
        sql += " AND d.id_group = ? AND t.id_faculty = ? and d.semester BETWEEN ? AND ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, idGroup);
                    preparedStatement.setInt(2, idFaculty);
                    preparedStatement.setInt(3, semester.getFrom());
                    preparedStatement.setInt(4, semester.getTo());
                },
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByCategoryCourse(List<TeacherCategory> teacherCategories, int course, int idFaculty, IntervalFilter semester) {
        String sql = "SELECT DISTINCT t.id, t.name, id_faculty, category, year, wage, is_asp, gender, age, kids, id_cathedra " +
                "FROM teachers t  INNER JOIN discipline d on t.id = d.id_teacher ";
        sql += MainFilterBuilder.getTeacherCategoryFilter(teacherCategories);
        sql += " AND d.course = ? AND t.id_faculty = ? and d.semester BETWEEN ? AND ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, course);
                    preparedStatement.setInt(2, idFaculty);
                    preparedStatement.setInt(3, semester.getFrom());
                    preparedStatement.setInt(4, semester.getTo());
                },
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getByExams(List<Integer> groupIds, String dName, int semester) {
        StringBuilder groupSelect = new StringBuilder();
        if (!groupIds.isEmpty()) {
            groupSelect.append(" AND d.id_group IN(");
            groupIds.forEach((groupId) -> groupSelect.append(groupId).append(","));
            groupSelect.deleteCharAt(groupSelect.length() - 1);
            groupSelect.append(")");
        }
        String sql = "SELECT DISTINCT t.id, t.name, id_faculty, category, year, wage, is_asp, gender, age, kids, id_cathedra  " +
                "FROM teachers t " +
                "INNER JOIN discipline d on t.id = d.id_teacher " +
                "INNER JOIN exam e on d.id = e.id_discipline " +
                "WHERE e.type = 'Exam' AND d.name = ? AND d.semester = ? ";
        sql += groupSelect;
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, dName);
                    preparedStatement.setInt(2, semester);
                },
                new TeacherRowMapper()

        );
    }

    @Override
    public List<Teacher> getHeadOfGwByCathedra(int idCathedra, List<TeacherCategory> teacherCategories) {
        String sql = "SELECT DISTINCT t.id, t.name, t.id_faculty, t.category, t.year,t.wage, t.is_asp, t.gender, t.age, t.kids, t.id_cathedra " +
                "FROM teachers t " +
                "INNER JOIN graduate_work gw on t.id = gw.id_teacher ";

        if (!teacherCategories.isEmpty()) {
            sql += MainFilterBuilder.getTeacherCategoryFilter(teacherCategories);
            sql += " AND t.id_cathedra = ? ";
        } else {
            sql += "WHERE t.id_cathedra = ? ";
        }

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idCathedra),
                new TeacherRowMapper()
        );
    }

    @Override
    public List<Teacher> getHeadOfGwByFaculty(int idFaculty, List<TeacherCategory> teacherCategories) {
        String sql = "SELECT DISTINCT t.id, t.name, t.id_faculty, t.category, t.year,t.wage, t.is_asp, t.gender, t.age, t.kids, t.id_cathedra " +
                "FROM teachers t " +
                "INNER JOIN graduate_work gw on t.id = gw.id_teacher ";

        if (!teacherCategories.isEmpty()) {
            sql += MainFilterBuilder.getTeacherCategoryFilter(teacherCategories);
            sql += " AND t.id_cathedra = ? ";
        } else {
            sql += "WHERE t.id_faculty = ? ";
        }

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idFaculty),
                new TeacherRowMapper()
        );
    }
}
