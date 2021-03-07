package com.stdb.dao;

import com.stdb.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(ID));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setName(resultSet.getString(NAME));
        return user;
    }
}
