package com.stdb.dao.graduatework;

import com.stdb.dao.cathedra.CathedraRowMapper;
import com.stdb.entity.Cathedra;
import com.stdb.entity.CombinedGW;
import com.stdb.entity.GraduateWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GraduateWorkDaoImpl implements GraduateWorkDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GraduateWorkDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public GraduateWork create(GraduateWork graduateWork) {
        String sql = "INSERT INTO graduate_work (id_student, id_teacher, year, name, description) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                graduateWork.getIdStudent(),
                graduateWork.getIdTeacher(),
                graduateWork.getYear(),
                graduateWork.getName(),
                graduateWork.getDescription()
        );
        return this.getByName(graduateWork.getName());
    }

    @Override
    public GraduateWork edit(GraduateWork graduateWork, int idGW) {
        String sql = "UPDATE graduate_work " +
                "SET id_student = ?, id_teacher = ?, year = ?, name = ?, description = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                graduateWork.getIdStudent(),
                graduateWork.getIdTeacher(),
                graduateWork.getYear(),
                graduateWork.getName(),
                graduateWork.getDescription(),
                idGW
        );
        return this.getByName(graduateWork.getName());
    }

    @Override
    public void delete(int idGW) {
        String sql = "DELETE FROM graduate_work WHERE id = ?";
        jdbcTemplate.update(
                sql,
                idGW
        );
    }

    @Override
    public GraduateWork getById(int idGW) {
        String sql = "SELECT id, id_student, id_teacher, year, name, description " +
                "FROM graduate_work WHERE id = ?";
        List<GraduateWork> graduateWorks = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idGW),
                new GraduateWorkRowMapper());
        return graduateWorks.size() == 1 ? graduateWorks.get(0) : null;
    }

    @Override
    public GraduateWork getByName(String name) {
        String sql = "SELECT id, id_student, id_teacher, year, name, description " +
                "FROM graduate_work WHERE name = ?";
        List<GraduateWork> graduateWorks = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new GraduateWorkRowMapper());
        return graduateWorks.size() == 1 ? graduateWorks.get(0) : null;
    }

    @Override
    public List<CombinedGW> getByCathedra(int idCathedra) {
        String sql = "SELECT DISTINCT s.id as id_student, s.name as student_name, gw.name as gw_name " +
                "FROM students s INNER JOIN graduate_work gw on s.id = gw.id_student " +
                "INNER JOIN teachers t on t.id = gw.id_teacher " +
                "WHERE t.id_cathedra = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idCathedra),
                new CombinedGWRowMapper()
        );
    }

    @Override
    public List<CombinedGW> getByTeacher(int idTeacher) {
        String sql = "SELECT DISTINCT s.id as id_student, s.name as student_name, gw.name as gw_name " +
                "FROM students s INNER JOIN graduate_work gw on s.id = gw.id_student " +
                "WHERE gw.id_teacher = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idTeacher),
                new CombinedGWRowMapper()
        );
    }
}
