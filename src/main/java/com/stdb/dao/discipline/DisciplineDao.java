package com.stdb.dao.discipline;

import com.stdb.entity.Discipline;
import com.stdb.entity.Teacher;

public interface DisciplineDao {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

    Discipline getByItems(Discipline discipline);
}
