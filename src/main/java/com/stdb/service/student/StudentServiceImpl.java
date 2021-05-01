package com.stdb.service.student;

import com.stdb.dao.student.StudentDao;
import com.stdb.entity.Student;
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
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    @Override
    public Student createStudent(Student student) {
        try {
            return studentDao.createStudent(student);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public Student editStudent(Student student, int studentId) {
        try {
            return studentDao.editStudent(student, studentId);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }

    }

    @Override
    public void deleteStudent(int studentId) {
        try {
            studentDao.deleteStudent(studentId);
        } catch (DataIntegrityViolationException ex) {
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public Student getById(int studentId) {
        return studentDao.getById(studentId);
    }

    @Override
    public Student getByName(String name) {
        return studentDao.getByName(name);
    }

    @Override
    public List<Student> getByGroup(String[] group, Map<String, Object> filters) {
        return studentDao.getByGroup(group, filters);
    }

    @Override
    public List<Student> getByCourse(Integer[] courses, Map<String, Object> filters) {
        return studentDao.getByCourse(courses, filters);
    }

    @Override
    public List<Student> getByDisciplineAndMark(List<Integer> groupIds, int idDiscipline, int mark) {
        return studentDao.getByDisciplineAndMark(groupIds, idDiscipline, mark);
    }

    @Override
    public List<Student> getByGroupAndMarks(List<Integer> groupIds, int idFaculty, int minMark, int semester) {
        return studentDao.getByGroupAndMarks(groupIds, idFaculty, minMark, semester);
    }

    @Override
    public List<Student> getByCourseAndMarks(int course, int idFaculty, int minMark, int semester) {
        return studentDao.getByCourseAndMarks(course, idFaculty, minMark, semester);
    }

    @Override
    public List<Student> getByGroupAndSemester(List<Integer> groupIds, IntervalFilter semester) {
        return studentDao.getByGroupAndSemester(groupIds, semester);
    }

    @Override
    public List<Student> getByMarkAndSemester(int mark, int idDiscipline, IntervalFilter semester) {
        return studentDao.getByMarkAndSemester(mark, idDiscipline, semester);
    }
}
