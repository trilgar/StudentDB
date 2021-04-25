package com.stdb.service.cathedra;

import com.stdb.entity.Cathedra;

import java.util.List;

public interface CathedraService {
    Cathedra createCathedra(Cathedra cathedra);

    Cathedra editCathedra(Cathedra cathedra, int idCathedra);

    void deleteCathedra(int idCathedra);

    Cathedra getById(int idCathedra);

    Cathedra getByName(String name);

    List<Cathedra> getByContainName(String name);
}
