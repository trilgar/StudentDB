package com.stdb.service.exam;

import com.stdb.entity.Exam;

import java.util.List;

public interface ExamService {
    Exam create(Exam exam);

    Exam edit(Exam exam, int idExam);

    void delete(int idExam);

    Exam getById(int idExam);

    List<Exam> getByName(String name);

    Exam getIds(int idDiscipline, int idStudent);
}
