package com.stdb.service.graduatework;

import com.stdb.entity.GraduateWork;

public interface GraduateWorkService {
    GraduateWork create(GraduateWork graduateWork);

    GraduateWork edit(GraduateWork graduateWork, int idGW);

    void delete(int idGW);

    GraduateWork getById(int idGW);

    GraduateWork getByName(String name);
}
