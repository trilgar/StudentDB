package com.stdb.service.teacher;

import com.stdb.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    Teacher create(Teacher teacher);

    Teacher edit(Teacher teacher, int idTeacher);

    void delete(int idTeacher);

    Teacher getByName(String name);

    List<Teacher> getByFaculty(String[] faculty, Map<String, Object> filters);

    List<Teacher> getByCathedra(String[] cathedras, Map<String, Object> filters);
}
