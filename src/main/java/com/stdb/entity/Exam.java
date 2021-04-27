package com.stdb.entity;

import lombok.Data;

@Data
public class Exam {
    private int id;
    private EType type;
    private int idDiscipline;
    private int idStudent;
    private String description;
    private int mark;
}
