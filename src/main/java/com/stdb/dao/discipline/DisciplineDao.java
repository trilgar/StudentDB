package com.stdb.dao.discipline;

import com.stdb.entity.Discipline;

public interface DisciplineDao {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

    Discipline getByName(String name);
}
