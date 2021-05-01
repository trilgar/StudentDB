package com.stdb.dao.student;

import com.stdb.entity.Student;
import com.stdb.helpers.IntervalFilter;

import java.util.List;
import java.util.Map;

public interface StudentDao {
    Student createStudent(Student student);

    Student editStudent(Student student, int studentId);

    void deleteStudent(int studentId);

    Student getById(int studentId);

    Student getByName(String name);

    List<Student> getByGroup(String[] group, Map<String, Object> filters);

    List<Student> getByCourse(Integer[] course, Map<String, Object> filters);

    // 7 task
    List<Student> getByDisciplineAndMark(List<Integer> groupIds, int idDiscipline, int mark);

    // 8 task
    List<Student> getByGroupAndMarks(List<Integer> groupIds, int idFaculty, int minMark, int semester);

    List<Student> getByCourseAndMarks(int course, int idFaculty, int minMark, int semester);

    // 10 task
    List<Student> getByGroupAndSemester(List<Integer> groupIds, IntervalFilter semester);

    List<Student> getByMarkAndSemester(int mark, int idDiscipline, IntervalFilter semester);
}
