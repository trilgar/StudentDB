package com.stdb.service.sciencework;

import com.stdb.entity.ScienceWork;

import java.util.List;

public interface ScienceWorkService {
    ScienceWork create(ScienceWork scienceWork);

    ScienceWork edit(ScienceWork scienceWork, int idScienceWork);

    void delete(int idScienceWork);

    ScienceWork getById(int idScienceWork);

    ScienceWork getByName(String name);

    List<ScienceWork> getWorksByItems(int idFaculty, int idCathedra);
}
