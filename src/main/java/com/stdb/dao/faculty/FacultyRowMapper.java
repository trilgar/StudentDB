package com.stdb.dao.faculty;

import com.stdb.entity.Faculty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyRowMapper implements RowMapper<Faculty> {
    private final static String ID = "id";
    private final static String NAME = "name";


    @Override
    public Faculty mapRow(ResultSet resultSet, int i) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setId(resultSet.getInt(ID));
        faculty.setName(resultSet.getString(NAME));
        return faculty;
    }
}
