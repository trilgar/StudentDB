package com.stdb.entity;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String name;
    private int idGroup;
    private int idFaculty;
    private int stipendium;
    private Gender gender;
    private int year;
    private int age;
    private int kids;
}
