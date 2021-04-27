package com.stdb.dao.ExamDao;

import com.stdb.entity.Exam;

public interface ExamDao {
    Exam create(Exam exam);

    Exam edit(Exam exam, int idExam);

    void delete(int idExam);

    Exam getById(int idExam);

}
