package com.stdb.service.teacher;

import com.stdb.entity.Teacher;
import com.stdb.entity.TeacherCategory;
import com.stdb.helpers.IntervalFilter;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    Teacher create(Teacher teacher);

    Teacher edit(Teacher teacher, int idTeacher);

    void delete(int idTeacher);

    Teacher getByName(String name);

    List<Teacher> getByFaculty(String[] faculty, Map<String, Object> filters);

    List<Teacher> getByCathedra(String[] cathedras, Map<String, Object> filters);

    List<Teacher> getByGroup(String dName, int idGroup, int idFaculty);

    List<Teacher> getByCourse(String dName, int course, int idFaculty);

    List<Teacher> getByCategoryGroup(List<TeacherCategory> teacherCategories, int idGroup, int idFaculty, IntervalFilter semester);

    List<Teacher> getByCategoryCourse(List<TeacherCategory> teacherCategories, int course, int idFaculty, IntervalFilter semester);

    List<Teacher> getByExams(int idGroup, String dName, int semester);

    List<Teacher> getHeadOfGwByCathedra(int idCathedra, List<TeacherCategory> teacherCategories);

    List<Teacher> getHeadOfGwByFaculty(int idFaculty, List<TeacherCategory> teacherCategories);
}
