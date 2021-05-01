package com.stdb.dao.teacher;

import com.stdb.entity.Teacher;
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
    public List<Teacher> getByFaculty(String[] faculties, Map<String, Object> filters) {
        StringBuilder facultiesFilter = new StringBuilder("WHERE (");
        for (String faculty : faculties) {
            facultiesFilter.append("fct.name = ").append("'").append(faculty).append("'").append(" OR ");
        }
        facultiesFilter.delete(facultiesFilter.length() - 4, facultiesFilter.length() - 1);
        facultiesFilter.append(") AND ");

        String sql = "SELECT tcr.id, tcr.name, tcr.id_faculty, tcr.category, tcr.year, tcr.wage, tcr.is_asp, tcr.gender, tcr.age, tcr.kids, tcr.id_cathedra " +
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

        String sql = "SELECT tcr.id, tcr.name, tcr.id_faculty, tcr.category, tcr.year, tcr.wage, tcr.is_asp, tcr.gender, tcr.age, tcr.kids, tcr.id_cathedra " +
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
}
