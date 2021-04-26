package com.stdb.dao.graduatework;

import com.stdb.entity.GraduateWork;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GraduateWorkRowMapper implements RowMapper<GraduateWork> {
    private static final String ID = "id";
    private static final String ID_STUDENT = "id_student";
    private static final String ID_TEACHER = "id_teacher";
    private static final String YEAR = "year";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Override
    public GraduateWork mapRow(ResultSet resultSet, int i) throws SQLException {
        GraduateWork graduateWork = new GraduateWork();
        graduateWork.setId(resultSet.getInt(ID));
        graduateWork.setIdStudent(resultSet.getInt(ID_STUDENT));
        graduateWork.setIdTeacher(resultSet.getInt(ID_TEACHER));
        graduateWork.setYear(resultSet.getInt(YEAR));
        graduateWork.setName(resultSet.getString(NAME));
        graduateWork.setDescription(resultSet.getString(DESCRIPTION));

        return graduateWork;
    }
}
