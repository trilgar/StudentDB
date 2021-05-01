package com.stdb.entity;

import lombok.Data;

@Data
public class DisciplineLoad {
    private String disciplineName;
    private int lectionHours;
    private int seminarHours;
    private int labWorkHours;
    private int consultancyHours;
    private int courseworkHours;
}
