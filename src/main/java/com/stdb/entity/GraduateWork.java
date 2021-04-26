package com.stdb.entity;

import lombok.Data;

@Data
public class GraduateWork {
    private int id;
    private int idStudent;
    private int idTeacher;
    private int year;
    private String name;
    private String description;
}
