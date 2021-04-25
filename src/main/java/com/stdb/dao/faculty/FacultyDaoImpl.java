package com.stdb.dao.faculty;

import com.stdb.entity.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FacultyDaoImpl implements FacultyDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        String sql = "INSERT INTO faculty (name) VALUES (?)";
        jdbcTemplate.update(
                sql,
                faculty.getName()
        );
        return this.getByName(faculty.getName());
    }

    @Override
    public Faculty editFaculty(Faculty faculty, int idFaculty) {
        String sql = "UPDATE faculty SET name = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                faculty.getName(),
                idFaculty
        );
        return this.getByName(faculty.getName());
    }

    @Override
    public void deleteFaculty(int idFaculty) {
        String sql = "DELETE FROM faculty WHERE id = ?";
        jdbcTemplate.update(
                sql,
                idFaculty
        );
    }

    @Override
    public Faculty getById(int idFaculty) {
        String sql = "SELECT id,name " +
                "FROM faculty WHERE id = ?";
        List<Faculty> faculties = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idFaculty),
                new FacultyRowMapper());
        return faculties.size() == 1 ? faculties.get(0) : null;
    }

    @Override
    public Faculty getByName(String name) {
        String sql = "SELECT id,name " +
                "FROM faculty WHERE name = ?";
        List<Faculty> faculties = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new FacultyRowMapper());
        return faculties.size() == 1 ? faculties.get(0) : null;
    }

    @Override
    public List<Faculty> getByContainName(String name) {
        String sql = "SELECT id,name " +
                "FROM faculty WHERE name Like concat('%',?,'%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new FacultyRowMapper());
    }
}
