package com.stdb.service.sciencework;

import com.stdb.dao.sciencework.ScienceWorkDao;
import com.stdb.entity.ScienceWork;
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
public class ScienceWorkServiceImpl implements ScienceWorkService {
    private final ScienceWorkDao scienceWorkDao;

    @Override
    public ScienceWork create(ScienceWork scienceWork) {
        try {
            return scienceWorkDao.create(scienceWork);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public ScienceWork edit(ScienceWork scienceWork, int idScienceWork) {
        try {
            return scienceWorkDao.edit(scienceWork, idScienceWork);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public void delete(int idScienceWork) {
        try {
            scienceWorkDao.delete(idScienceWork);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public ScienceWork getById(int idScienceWork) {
        return scienceWorkDao.getById(idScienceWork);
    }

    @Override
    public ScienceWork getByName(String name) {
        return scienceWorkDao.getByName(name);
    }

    @Override
    public List<ScienceWork> getWorksByItems(int idFaculty, int idCathedra) {
        return scienceWorkDao.getWorksByItems(idFaculty, idCathedra);
    }

}
