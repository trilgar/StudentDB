package com.stdb.service.faculty;

import com.stdb.dao.faculty.FacultyDao;
import com.stdb.entity.Faculty;
import com.stdb.entity.Group;
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
public class FacultyServiceImpl implements FacultyService{
    private final FacultyDao facultyDao;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        Faculty newFaculty;
        try{
            newFaculty = facultyDao.createFaculty(faculty);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
        return newFaculty;
    }

    @Override
    public Faculty editFaculty(Faculty faculty, int idFaculty) {
        Faculty newFaculty;
        try{
            newFaculty = facultyDao.editFaculty(faculty, idFaculty);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
        return newFaculty;
    }

    @Override
    public void deleteFaculty(int idFaculty) {
        try{
            facultyDao.deleteFaculty(idFaculty);
        }
        catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public Faculty getById(int idFaculty) {
        return facultyDao.getById(idFaculty);
    }

    @Override
    public Faculty getByName(String name) {
        return facultyDao.getByName(name);
    }

    @Override
    public List<Faculty> getByContainName(String name) {
        return facultyDao.getByContainName(name);
    }
}
