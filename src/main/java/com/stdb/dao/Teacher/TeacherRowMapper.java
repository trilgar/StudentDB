package com.stdb.dao.Teacher;

import com.stdb.entity.AspStatus;
import com.stdb.entity.Gender;
import com.stdb.entity.Teacher;
import com.stdb.entity.TeacherCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherRowMapper implements RowMapper<Teacher> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FACULTY = "id_faculty";
    private static final String CATEGORY = "category";
    private static final String YEAR = "year";
    private static final String WAGE = "wage";
    private static final String IS_ASP = "is_asp";
    private static final String GENDER = "gender";
    private static final String AGE = "age";
    private static final String KIDS = "kids";
    private static final String CATHEDRA = "id_cathedra";

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt(ID));
        teacher.setName(resultSet.getString(NAME));
        teacher.setFacultyId(resultSet.getInt(FACULTY));
        teacher.setCategory(TeacherCategory.valueOf(resultSet.getString(CATEGORY)));
        teacher.setYear(resultSet.getInt(YEAR));
        teacher.setWage(resultSet.getInt(WAGE));
        teacher.setAsp(AspStatus.valueOf(resultSet.getString(IS_ASP)));
        teacher.setGender(Gender.valueOf(resultSet.getString(GENDER)));
        teacher.setAge(resultSet.getInt(AGE));
        teacher.setKids(resultSet.getInt(KIDS));
        teacher.setIdCathedra(resultSet.getInt(CATHEDRA));

        return teacher;
    }
}
