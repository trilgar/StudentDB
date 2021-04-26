package com.stdb.entity;

import lombok.Data;

@Data
public class Discipline {
    private int id;
    private DType type;
    private int idTeacher;
    private int idGroup;
    private String name;
    private int hours;
    private int course;
    private int semester;
}
