package com.stdb.dao.discipline;

import com.stdb.dao.student.StudentRowMapper;
import com.stdb.entity.Discipline;
import com.stdb.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DisciplineDaoImpl implements DisciplineDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DisciplineDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Discipline create(Discipline discipline) {
        String sql = "INSERT INTO discipline (type, id_teacher, id_group, name, hours, course, semester) " +
                "VALUES (?::d_type, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                discipline.getType().toString(),
                discipline.getIdTeacher(),
                discipline.getIdGroup(),
                discipline.getName(),
                discipline.getHours(),
                discipline.getCourse(),
                discipline.getSemester()
        );
        return this.getByName(discipline.getName());
    }

    @Override
    public Discipline edit(Discipline discipline, int idDiscipline) {
        String sql = "UPDATE discipline SET type = ?::d_type, id_teacher = ?, " +
                "id_group = ?, name = ?, hours = ?, course = ?, semester = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                discipline.getType().toString(),
                discipline.getIdTeacher(),
                discipline.getIdGroup(),
                discipline.getName(),
                discipline.getHours(),
                discipline.getCourse(),
                discipline.getSemester(),
                idDiscipline
        );
        return this.getByName(discipline.getName());
    }

    @Override
    public void delete(int idDiscipline) {
        String sql = "DELETE FROM discipline WHERE id = ?";
        jdbcTemplate.update(sql, idDiscipline);
    }

    @Override
    public Discipline getById(int idDiscipline) {
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester " +
                "FROM discipline WHERE id = ?";
        List<Discipline> disciplines = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idDiscipline),
                new DisciplineRowMapper()
        );
        return disciplines.size() == 1 ? disciplines.get(0) : null;
    }

    @Override
    public Discipline getByName(String name) {
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester " +
                "FROM discipline WHERE name = ?";
        List<Discipline> disciplines = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new DisciplineRowMapper()
        );
        return disciplines.size() == 1 ? disciplines.get(0) : null;
    }
}
