package com.stdb.service.discipline;

import com.stdb.dao.discipline.DisciplineDao;
import com.stdb.entity.Discipline;
import com.stdb.entity.Teacher;
import com.stdb.helpers.exceptions.ForeignKeyViolationException;
import com.stdb.helpers.exceptions.NameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {
    private final DisciplineDao disciplineDao;

    @Override
    public Discipline create(Discipline discipline) {
        try {
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

}
