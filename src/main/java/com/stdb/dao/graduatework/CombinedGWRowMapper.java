package com.stdb.dao.graduatework;

import com.stdb.entity.CombinedGW;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CombinedGWRowMapper implements RowMapper<CombinedGW> {
    private static final String ID_STUDENT = "id_student";
    private static final String STUDENT_NAME = "student_name";
    private static final String GW_NAME = "gw_name";

    @Override
    public CombinedGW mapRow(ResultSet resultSet, int i) throws SQLException {
        CombinedGW combinedGW = new CombinedGW();
        combinedGW.setIdStudent(resultSet.getInt(ID_STUDENT));
        combinedGW.setStudentName(resultSet.getString(STUDENT_NAME));
        combinedGW.setGraduateWorkName(resultSet.getString(GW_NAME));
        return combinedGW;
    }
}
