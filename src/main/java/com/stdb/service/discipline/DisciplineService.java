package com.stdb.service.discipline;

import com.stdb.entity.Discipline;
import com.stdb.entity.DisciplineLoad;
import com.stdb.entity.Teacher;

import java.util.List;

public interface DisciplineService {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

    List<DisciplineLoad> getTeachersLoad(int semester, int idTeacher);

    List<DisciplineLoad> getLoadByCathedra(int semester, int idCathedra);
}
