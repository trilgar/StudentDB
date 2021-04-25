package com.stdb.dao.sciencework;

import com.stdb.entity.ScienceWork;
import com.stdb.entity.SwType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScienceWorkRowMapper implements RowMapper<ScienceWork> {
    private static final String ID = "id";
    private static final String ID_TEACHER = "id_teacher";
    private static final String YEAR = "year";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TEACHER_NAME = "tcr_name";
    private static final String TYPE = "type";

    @Override
    public ScienceWork mapRow(ResultSet resultSet, int i) throws SQLException {
        ScienceWork scienceWork = new ScienceWork();
        scienceWork.setId(resultSet.getInt(ID));
        scienceWork.setIdTeacher(resultSet.getInt(ID_TEACHER));
        scienceWork.setName(resultSet.getString(NAME));
        scienceWork.setYear(resultSet.getInt(YEAR));
        if (!resultSet.getString(DESCRIPTION).contains(resultSet.getString(TEACHER_NAME))) {
            scienceWork.setDescription(resultSet.getString(DESCRIPTION) + "\nTeacher Name: "
                    + resultSet.getString(TEACHER_NAME));
        } else {
            scienceWork.setDescription(resultSet.getString(DESCRIPTION));
        }

        scienceWork.setType(SwType.valueOf(resultSet.getString(TYPE)));
        return scienceWork;
    }
}
