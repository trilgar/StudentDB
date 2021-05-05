package com.stdb.dao.discipline;

import com.stdb.entity.Discipline;
import com.stdb.entity.DisciplineLoad;

import java.util.List;

public interface DisciplineDao {
    Discipline create(Discipline discipline);

    Discipline edit(Discipline discipline, int idDiscipline);

    void delete(int idDiscipline);

    Discipline getById(int idDiscipline);

    Discipline getByItems(Discipline discipline);

    List<Discipline> getByName(String name);

    List<DisciplineLoad> getTeachersLoad(int semester, int idTeacher);

    List<DisciplineLoad> getLoadByCathedra(int semester, int idCathedra);

    List<Discipline> getByGroups(List<Integer> groups);

    List<Discipline> getByCourses(List<Integer> courses);
}
