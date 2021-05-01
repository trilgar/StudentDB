package com.stdb.service.graduatework;

import com.stdb.dao.graduatework.GraduateWorkDao;
import com.stdb.entity.CombinedGW;
import com.stdb.entity.GraduateWork;
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
public class GraduateWorkServiceImpl implements GraduateWorkService {
    private final GraduateWorkDao graduateWorkDao;

    @Override
    public GraduateWork create(GraduateWork graduateWork) {
        try {
            return graduateWorkDao.create(graduateWork);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public GraduateWork edit(GraduateWork graduateWork, int idGW) {
        try {
            return graduateWorkDao.edit(graduateWork, idGW);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public void delete(int idGW) {
        try {
            graduateWorkDao.delete(idGW);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public GraduateWork getById(int idGW) {
        return graduateWorkDao.getById(idGW);
    }

    @Override
    public GraduateWork getByName(String name) {
        return graduateWorkDao.getByName(name);
    }

    @Override
    public List<CombinedGW> getByCathedra(int idCathedra) {
        return graduateWorkDao.getByCathedra(idCathedra);
    }

    @Override
    public List<CombinedGW> getByTeacher(int idTeacher) {
        return graduateWorkDao.getByTeacher(idTeacher);
    }
}
