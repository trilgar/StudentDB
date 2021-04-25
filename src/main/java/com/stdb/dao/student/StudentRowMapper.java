package com.stdb.dao.student;

import com.stdb.entity.Gender;
import com.stdb.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String ID_GROUP = "id_group";
    private final static String ID_FACULTY = "id_faculty";
    private final static String STIPENDIUM = "stipendium";
    private final static String GENDER = "gender";
    private final static String AGE = "age";
    private final static String KIDS = "kids";

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getInt(ID));
        student.setName(resultSet.getString(NAME));
        student.setIdGroup(resultSet.getInt(ID_GROUP));
        student.setIdFaculty(resultSet.getInt(ID_FACULTY));
        student.setStipendium(resultSet.getInt(STIPENDIUM));
        student.setGender(Gender.valueOf(resultSet.getString(GENDER)));
        student.setAge(resultSet.getInt(AGE));
        student.setKids(resultSet.getInt(KIDS));

        return student;
    }
}
