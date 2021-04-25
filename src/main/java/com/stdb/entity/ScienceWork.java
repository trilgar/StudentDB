package com.stdb.entity;

import lombok.Data;

@Data
public class ScienceWork {
    private int id;
    private SwType type;
    private int idTeacher;
    private int year;
    private String name;
    private String description;
}
