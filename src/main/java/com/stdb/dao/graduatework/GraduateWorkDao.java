package com.stdb.dao.graduatework;

import com.stdb.entity.GraduateWork;

public interface GraduateWorkDao {
    GraduateWork create(GraduateWork graduateWork);

    GraduateWork edit(GraduateWork graduateWork, int idGW);

    void delete(int idGW);

    GraduateWork getById(int idGW);

    GraduateWork getByName(String name);
}
