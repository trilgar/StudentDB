package com.stdb.dao.sciencework;

import com.stdb.dao.graduatework.CombinedGWRowMapper;
import com.stdb.entity.CombinedGW;
import com.stdb.entity.ScienceWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ScienceWorkDaoImpl implements ScienceWorkDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScienceWorkDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScienceWork create(ScienceWork scienceWork) {
        String sql = "INSERT INTO science_work (type, id_teacher, year, name, description) VALUES (?::s_work, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                scienceWork.getType().toString(),
                scienceWork.getIdTeacher(),
                scienceWork.getYear(),
                scienceWork.getName(),
                scienceWork.getDescription()
        );
        return getByName(scienceWork.getName());
    }

    @Override
    public ScienceWork edit(ScienceWork scienceWork, int idScienceWork) {
        String sql = "UPDATE science_work SET type = ?::s_work, id_teacher = ?, year = ?, name = ?, description = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                scienceWork.getType().toString(),
                scienceWork.getIdTeacher(),
                scienceWork.getYear(),
                scienceWork.getName(),
                scienceWork.getDescription(),
                idScienceWork
        );
        return getByName(scienceWork.getName());
    }

    @Override
    public void delete(int idScienceWork) {
        String sql = "DELETE FROM science_work WHERE id = ? ";
        jdbcTemplate.update(sql, idScienceWork);
    }

    @Override
    public ScienceWork getById(int idScienceWork) {
        String sql = "SELECT Distinct sw.id, sw.type, sw.id_teacher, sw.year, sw.name, sw.description, tcr.name as tcr_name " +
                "FROM science_work sw INNER JOIN teachers tcr on tcr.id = sw.id_teacher WHERE sw.id = ?";
        List<ScienceWork> scienceWorks = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idScienceWork),
                new ScienceWorkRowMapper()
        );
        return scienceWorks.size() == 1 ? scienceWorks.get(0) : null;
    }

    @Override
    public ScienceWork getByName(String name) {
        String sql = "SELECT Distinct sw.id, sw.type, sw.id_teacher, sw.year, sw.name, sw.description, tcr.name as tcr_name " +
                "FROM science_work sw INNER JOIN teachers tcr on tcr.id = sw.id_teacher WHERE sw.name = ?";
        List<ScienceWork> scienceWorks = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new ScienceWorkRowMapper()
        );
        return scienceWorks.size() == 1 ? scienceWorks.get(0) : null;
    }

    @Override
    public List<ScienceWork> getWorksByItems(int idFaculty, int idCathedra) {
        String sql = "SELECT DISTINCT sw.id, sw.type, sw.id_teacher, sw.year, sw.name, sw.description, tcr.name as tcr_name " +
                " FROM science_work sw " +
                "INNER JOIN teachers tcr on tcr.id = sw.id_teacher " +
                "INNER JOIN cathedra cfd on cfd.id = tcr.id_cathedra " +
                "INNER JOIN faculty fct on fct.id = tcr.id_faculty " +
                "WHERE fct.id = ? AND cfd.id = ? ";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, idFaculty);
                    preparedStatement.setInt(2, idCathedra);
                },
                new ScienceWorkRowMapper());
    }

    @Override
    public List<ScienceWork> getByContainName(String name) {
        String sql = "SELECT Distinct sw.id, sw.type, sw.id_teacher, sw.year, sw.name, sw.description, tcr.name as tcr_name " +
                "FROM science_work sw INNER JOIN teachers tcr on tcr.id = sw.id_teacher WHERE sw.name LIKE concat('%', ?, '%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new ScienceWorkRowMapper()
        );
    }
}
