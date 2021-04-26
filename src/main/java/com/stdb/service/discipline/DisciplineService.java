package com.stdb.service.discipline;

import com.stdb.entity.Discipline;

public interface DisciplineService {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

    Discipline getByName(String name);
}
