package com.stdb.dao.teacher;

import com.stdb.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherDao {
    Teacher create(Teacher teacher);

    Teacher edit(Teacher teacher, int idTeacher);

    void delete(int idTeacher);

    Teacher getByName(String name);

    List<Teacher> getByFaculty(String[] faculties, Map<String, Object> filters);

    List<Teacher> getByCathedra(String[] cathedras, Map<String, Object> filters);

    List<Teacher> getByGroup(String dName, int idGroup, int idFaculty);

    List<Teacher> getByCourse(String dName, int course, int idFaculty);

}
