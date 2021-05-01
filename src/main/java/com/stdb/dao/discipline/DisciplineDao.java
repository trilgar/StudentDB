package com.stdb.dao.discipline;

import com.stdb.entity.Discipline;
import com.stdb.entity.DisciplineLoad;
import com.stdb.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface DisciplineDao {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

    Discipline getByItems(Discipline discipline);

    List<DisciplineLoad> getTeachersLoad(int semester, int idTeacher);

    List<DisciplineLoad> getLoadByCathedra(int semester, int idCathedra);
}
