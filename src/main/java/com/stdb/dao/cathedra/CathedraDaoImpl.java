package com.stdb.dao.cathedra;

import com.stdb.entity.Cathedra;
import com.stdb.helpers.IntervalFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CathedraDaoImpl implements CathedraDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CathedraDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Cathedra createCathedra(Cathedra cathedra) {
        String sql = "INSERT INTO cathedra (name) VALUES (?)";
        jdbcTemplate.update(
                sql,
                cathedra.getName()
        );
        return this.getByName(cathedra.getName());
    }

    @Override
    public Cathedra editCathedra(Cathedra cathedra, int idCathedra) {
        String sql = "UPDATE cathedra SET name = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                cathedra.getName(),
                idCathedra
        );
        return this.getByName(cathedra.getName());
    }

    @Override
    public void deleteCathedra(int idCathedra) {
        String sql = "DELETE FROM cathedra WHERE id = ?";
        jdbcTemplate.update(
                sql,
                idCathedra
        );
    }

    @Override
    public Cathedra getById(int idCathedra) {
        String sql = "SELECT id,name " +
                "FROM cathedra WHERE id = ?";
        List<Cathedra> cathedras = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idCathedra),
                new CathedraRowMapper());
        return cathedras.size() == 1 ? cathedras.get(0) : null;
    }

    @Override
    public Cathedra getByName(String name) {
        String sql = "SELECT id,name " +
                "FROM cathedra WHERE name = ?";
        List<Cathedra> cathedras = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new CathedraRowMapper());
        return cathedras.size() == 1 ? cathedras.get(0) : null;
    }

    @Override
    public List<Cathedra> getByContainName(String name) {
        String sql = "SELECT id,name " +
                "FROM cathedra WHERE name Like concat('%',?,'%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new CathedraRowMapper()
        );
    }

    @Override
    public List<Cathedra> getByGroup(int idGroup, int idFaculty, IntervalFilter semester) {
        String sql = "SELECT DISTINCT ctd.id,ctd.name FROM cathedra ctd " +
                "INNER JOIN teachers t on ctd.id = t.id_cathedra " +
                "INNER JOIN discipline d on t.id = d.id_teacher " +
                "WHERE d.id_group = ? AND t.id_faculty = ? AND d.semester BETWEEN ? AND ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, idGroup);
                    preparedStatement.setInt(2, idFaculty);
                    preparedStatement.setInt(3, semester.getFrom());
                    preparedStatement.setInt(4, semester.getTo());
                },
                new CathedraRowMapper()
        );
    }

    @Override
    public List<Cathedra> getByCourse(int idCourse, int idFaculty, IntervalFilter semester) {
        String sql = "SELECT DISTINCT ctd.id,ctd.name FROM cathedra ctd " +
                "INNER JOIN teachers t on ctd.id = t.id_cathedra " +
                "INNER JOIN discipline d on t.id = d.id_teacher " +
                "WHERE d.course = ? AND t.id_faculty = ? AND d.semester BETWEEN ? AND ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, idCourse);
                    preparedStatement.setInt(2, idFaculty);
                    preparedStatement.setInt(3, semester.getFrom());
                    preparedStatement.setInt(4, semester.getTo());
                },
                new CathedraRowMapper()
        );
    }
}
