package com.stdb.dao.discipline;

import com.stdb.entity.DType;
import com.stdb.entity.Discipline;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisciplineRowMapper implements RowMapper<Discipline> {
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String ID_TEACHER = "id_teacher";
    private static final String ID_GROUP = "id_group";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";
    private static final String SEMESTER = "semester";
    private static final String HOURS = "hours";

    @Override
    public Discipline mapRow(ResultSet resultSet, int i) throws SQLException {
        Discipline discipline = new Discipline();
        discipline.setId(resultSet.getInt(ID));
        discipline.setType(DType.valueOf(resultSet.getString(TYPE)));
        discipline.setIdTeacher(resultSet.getInt(ID_TEACHER));
        discipline.setIdGroup(resultSet.getInt(ID_GROUP));
        discipline.setName(resultSet.getString(NAME));
        discipline.setCourse(resultSet.getInt(COURSE));
        discipline.setSemester(resultSet.getInt(SEMESTER));
        discipline.setHours(resultSet.getInt(HOURS));

        return discipline;
    }
}
