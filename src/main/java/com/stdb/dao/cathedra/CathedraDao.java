package com.stdb.dao.cathedra;

import com.stdb.entity.Cathedra;
import com.stdb.helpers.IntervalFilter;

import java.util.List;

public interface CathedraDao {
    Cathedra createCathedra(Cathedra cathedra);

    Cathedra editCathedra(Cathedra cathedra, int idCathedra);

    void deleteCathedra(int idCathedra);

    Cathedra getById(int idCathedra);

    Cathedra getByName(String name);

    List<Cathedra> getByContainName(String name);

    List<Cathedra> getByGroup(int idGroup, int idFaculty, IntervalFilter semester);

    List<Cathedra> getByCourse(int idCourse, int idFaculty, IntervalFilter semester);
}
