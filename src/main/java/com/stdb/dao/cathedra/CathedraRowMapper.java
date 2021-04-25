package com.stdb.dao.cathedra;

import com.stdb.entity.Cathedra;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CathedraRowMapper implements RowMapper<Cathedra> {
    private final static String ID = "id";
    private final static String NAME = "name";

    @Override
    public Cathedra mapRow(ResultSet resultSet, int i) throws SQLException {
        Cathedra cathedra = new Cathedra();
        cathedra.setId(resultSet.getInt(ID));
        cathedra.setName(resultSet.getString(NAME));
        return cathedra;
    }
}
