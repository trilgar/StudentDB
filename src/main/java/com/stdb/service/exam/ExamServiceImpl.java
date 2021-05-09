package com.stdb.service.exam;

import com.stdb.dao.exam.ExamDao;
import com.stdb.entity.Exam;
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
public class ExamServiceImpl implements ExamService {
    private final ExamDao examDao;

    @Override
    public Exam create(Exam exam) {
        try {
            Exam duplicate = examDao.getIds(exam.getIdDiscipline(), exam.getIdStudent());
            if (duplicate != null) {
                return duplicate;
            }
            return examDao.create(exam);
        } catch (DataIntegrityViolationException exception) {
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public Exam edit(Exam exam, int idExam) {
        try {
            return examDao.edit(exam, idExam);
        } catch (DataIntegrityViolationException exception) {
            throw new NameAlreadyExistsException();
        }
    }

    @Override
    public void delete(int idExam) {
        try {
            examDao.delete(idExam);
        } catch (DataIntegrityViolationException exception) {
            throw new ForeignKeyViolationException();
        }
    }

    @Override
    public Exam getById(int idExam) {
        return examDao.getById(idExam);
    }

    @Override
    public List<Exam> getByName(String name) {
        return examDao.getByName(name);
    }

    @Override
    public Exam getIds(int idDiscipline, int idStudent) {
        return examDao.getIds(idDiscipline, idStudent);
    }
}
