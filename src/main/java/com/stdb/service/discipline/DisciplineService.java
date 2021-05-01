package com.stdb.service.discipline;

import com.stdb.entity.Discipline;
import com.stdb.entity.Teacher;

public interface DisciplineService {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

}
