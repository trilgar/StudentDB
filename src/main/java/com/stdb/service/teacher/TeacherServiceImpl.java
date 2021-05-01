package com.stdb.service.teacher;

import com.stdb.dao.teacher.TeacherDao;
import com.stdb.entity.Teacher;
import com.stdb.entity.TeacherCategory;
import com.stdb.helpers.IntervalFilter;
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
public class TeacherServiceImpl implements TeacherService {
    private final TeacherDao teacherDao;

    @Override
    public Teacher create(Teacher teacher) {
        try {
            return teacherDao.create(teacher);
        } catch (DataIntegrityViolationException ex) {
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public Teacher edit(Teacher teacher, int idTeacher) {
        try {
            return teacherDao.edit(teacher, idTeacher);
        } catch (DataIntegrityViolationException ex) {
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public void delete(int idTeacher) {
        try {
            teacherDao.delete(idTeacher);
        } catch (DataIntegrityViolationException ex) {
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

    @Override
    public List<Teacher> getByGroup(String dName, int idGroup, int idFaculty) {
        return teacherDao.getByGroup(dName, idGroup, idFaculty);
    }

    @Override
    public List<Teacher> getByCourse(String dName, int course, int idFaculty) {
        return teacherDao.getByCourse(dName, course, idFaculty);
    }

    @Override
    public List<Teacher> getByCategoryGroup(List<TeacherCategory> teacherCategories, int idGroup, int idFaculty, IntervalFilter semester) {
        return teacherDao.getByCategoryGroup(teacherCategories, idGroup, idFaculty, semester);
    }

    @Override
    public List<Teacher> getByCategoryCourse(List<TeacherCategory> teacherCategories, int course, int idFaculty, IntervalFilter semester) {
        return teacherDao.getByCategoryCourse(teacherCategories, course, idFaculty, semester);
    }

    @Override
    public List<Teacher> getByExams(int idGroup, String dName, int semester) {
        return teacherDao.getByExams(idGroup, dName, semester);
    }
}
