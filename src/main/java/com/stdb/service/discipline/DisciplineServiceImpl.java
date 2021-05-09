package com.stdb.service.discipline;

import com.stdb.dao.discipline.DisciplineDao;
import com.stdb.entity.Discipline;
import com.stdb.entity.DisciplineLoad;
import com.stdb.helpers.exceptions.ForeignKeyViolationException;
import com.stdb.helpers.exceptions.NameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {
    private final DisciplineDao disciplineDao;

    @Override
    public Discipline create(Discipline discipline) {
        try {
            Discipline duplicate = disciplineDao.getByItems(discipline);
            if (duplicate != null) {
                return duplicate;
            }
            return disciplineDao.create(discipline);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public Discipline edit(Discipline discipline, int idDiscipline) {
        try {
            return disciplineDao.edit(discipline, idDiscipline);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public void delete(int idDiscipline) {
        try {
            disciplineDao.delete(idDiscipline);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new ForeignKeyViolationException();
        }

    }

    @Override
    public Discipline getById(int idDiscipline) {
        return disciplineDao.getById(idDiscipline);
    }

    @Override
    public List<DisciplineLoad> getTeachersLoad(int semester, int idTeacher) {
        return disciplineDao.getTeachersLoad(semester, idTeacher);
    }

    @Override
    public List<DisciplineLoad> getLoadByCathedra(int semester, int idCathedra) {
        return disciplineDao.getLoadByCathedra(semester, idCathedra);
    }

    @Override
    public List<Discipline> getByName(String name) {
        return disciplineDao.getByName(name);
    }

    @Override
    public List<Discipline> getByGroups(List<Integer> groups) {
        return disciplineDao.getByGroups(groups);
    }

    @Override
    public List<Discipline> getByCourses(List<Integer> courses) {
        return disciplineDao.getByCourses(courses);
    }
}
