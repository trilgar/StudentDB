package com.stdb.service.cathedra;

import com.stdb.dao.cathedra.CathedraDao;
import com.stdb.entity.Cathedra;
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
public class CathedraServiceImpl implements CathedraService{
    private final CathedraDao cathedraDao;

    @Override
    public Cathedra createCathedra(Cathedra cathedra) {
        Cathedra newCathedra;
        try{
            newCathedra = cathedraDao.createCathedra(cathedra);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
        return newCathedra;
    }

    @Override
    public Cathedra editCathedra(Cathedra cathedra, int idCathedra) {
        Cathedra newCathedra;
        try{
            newCathedra = cathedraDao.editCathedra(cathedra,idCathedra);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
        return newCathedra;
    }

    @Override
    public void deleteCathedra(int idCathedra) {
        try{
            cathedraDao.deleteCathedra(idCathedra);
        }
        catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public Cathedra getById(int idCathedra) {
        return cathedraDao.getById(idCathedra);
    }

    @Override
    public Cathedra getByName(String name) {
        return cathedraDao.getByName(name);
    }

    @Override
    public List<Cathedra> getByContainName(String name) {
        return cathedraDao.getByContainName(name);
    }
}
