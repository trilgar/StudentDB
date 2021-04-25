package com.stdb.service.teacher;

import com.stdb.dao.Teacher.TeacherDao;
import com.stdb.entity.Teacher;
import com.stdb.helpers.exceptions.ForeignKeyViolationException;
import com.stdb.helpers.exceptions.NameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{
    private final TeacherDao teacherDao;

    @Override
    public Teacher create(Teacher teacher) {
        try{
            return teacherDao.create(teacher);
        }
        catch (DataIntegrityViolationException ex){
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public Teacher edit(Teacher teacher, int idTeacher) {
        try{
            return teacherDao.edit(teacher, idTeacher);
        }
        catch (DataIntegrityViolationException ex){
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public void delete(int idTeacher) {
        try{
            teacherDao.delete(idTeacher);
        }
        catch (DataIntegrityViolationException ex){
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public Teacher getByName(String name) {
        return teacherDao.getByName(name);
    }

    @Override
    public List<Teacher> getByFaculty(String[] faculty, Map<String, Object> filters) {
        return teacherDao.getByFaculty(faculty, filters);
    }

    @Override
    public List<Teacher> getByCathedra(String[] cathedras, Map<String, Object> filters) {
        return teacherDao.getByCathedra(cathedras, filters);
    }
}
