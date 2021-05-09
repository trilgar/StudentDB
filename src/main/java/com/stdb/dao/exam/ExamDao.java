package com.stdb.dao.exam;

import com.stdb.entity.Exam;

import java.util.List;

public interface ExamDao {
    Exam create(Exam exam);

    Exam edit(Exam exam, int idExam);

    void delete(int idExam);

    Exam getById(int idExam);

    Exam getIds(int idDiscipline, int idStudent);

    List<Exam> getByName(String name);

}
