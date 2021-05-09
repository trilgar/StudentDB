package com.stdb.service.graduatework;

import com.stdb.entity.CombinedGW;
import com.stdb.entity.GraduateWork;

import java.util.List;

public interface GraduateWorkService {
    GraduateWork create(GraduateWork graduateWork);

    GraduateWork edit(GraduateWork graduateWork, int idGW);

    void delete(int idGW);

    GraduateWork getById(int idGW);

    GraduateWork getByName(String name);

    List<CombinedGW> getByCathedra(int idCathedra);

    List<CombinedGW> getByTeacher(int idTeacher);

    List<GraduateWork> getByContainName(String name);
}
