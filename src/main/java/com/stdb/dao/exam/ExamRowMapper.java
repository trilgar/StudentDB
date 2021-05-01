package com.stdb.dao.exam;

import com.stdb.entity.EType;
import com.stdb.entity.Exam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamRowMapper implements RowMapper<Exam> {
    private final static String ID = "id";
    private final static String TYPE = "type";
    private final static String ID_STUDENT = "id_student";
    private final static String ID_DISCIPLINE = "id_discipline";
    private final static String DESCRIPTION = "description";
    private final static String MARK = "mark";

    @Override
    public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
        Exam exam = new Exam();
        exam.setId(resultSet.getInt(ID));
        exam.setType(EType.valueOf(resultSet.getString(TYPE)));
        exam.setIdDiscipline(resultSet.getInt(ID_DISCIPLINE));
        exam.setIdStudent(resultSet.getInt(ID_STUDENT));
        exam.setDescription(resultSet.getString(DESCRIPTION));
        exam.setMark(resultSet.getInt(MARK));

        return exam;
    }
}
