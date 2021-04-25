package com.stdb.entity;

import lombok.Data;

@Data
public class Teacher {
    private int id;
    private String name;
    private int facultyId;
    private TeacherCategory category;
    private int year;
    private int wage;
    private AspStatus asp;
    private Gender gender;
    private int age;
    private int kids;
    private int idCathedra;
}
