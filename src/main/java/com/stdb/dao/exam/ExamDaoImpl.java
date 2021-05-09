package com.stdb.dao.exam;

import com.stdb.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ExamDaoImpl implements ExamDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExamDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Exam create(Exam exam) {
        String sql = "INSERT INTO exam (type, id_discipline, id_student, description, mark) " +
                "VALUES (?::e_type, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                exam.getType().toString(),
                exam.getIdDiscipline(),
                exam.getIdStudent(),
                exam.getDescription(),
                exam.getMark()
        );
        return this.getIds(exam.getIdDiscipline(), exam.getIdStudent());
    }

    @Override
    public Exam edit(Exam exam, int idExam) {
        String sql = "UPDATE exam SET type = ?::e_type, id_discipline = ?, id_student = ?, description = ?, mark = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                exam.getType().toString(),
                exam.getIdDiscipline(),
                exam.getIdStudent(),
                exam.getDescription(),
                exam.getMark(),
                idExam
        );
        return this.getIds(exam.getIdDiscipline(), exam.getIdStudent());
    }

    @Override
    public void delete(int idExam) {
        String sql = "DELETE FROM cathedra WHERE id = ?";
        jdbcTemplate.update(
                sql,
                idExam
        );
    }

    @Override
    public Exam getById(int idExam) {
        String sql = "SELECT id, type, id_discipline, id_student, description, mark " +
                "FROM exam WHERE id = ?";
        List<Exam> exams = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idExam),
                new ExamRowMapper());
        return exams.size() == 1 ? exams.get(0) : null;
    }
    @Override
    public Exam getIds(int idDiscipline, int idStudent) {
        String sql = "SELECT id, type, id_discipline, id_student, description, mark " +
                "FROM exam WHERE id_discipline = ? AND id_student = ?";
        List<Exam> exams = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, idDiscipline);
                    preparedStatement.setInt(2, idStudent);
                },
                new ExamRowMapper());
        return exams.size() == 1 ? exams.get(0) : null;
    }

    @Override
    public List<Exam> getByName(String name) {
        String sql = "SELECT id, type, id_discipline, id_student, description, mark FROM exam WHERE exam.description LIKE concat('%', ?, '%') ";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new ExamRowMapper()
        );
    }
}
