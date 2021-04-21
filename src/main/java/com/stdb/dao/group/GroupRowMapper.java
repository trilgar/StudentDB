package com.stdb.dao.group;

import com.stdb.entity.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {
    private final String ID = "id";
    private final String NAME = "name";
    private final String COURSE = "course";
    private final String YEAR = "year";
    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt(ID));
        group.setName(resultSet.getString(NAME));
        group.setCourse(resultSet.getInt(COURSE));
        group.setYear(resultSet.getInt(YEAR));
        return group;
    }
}
