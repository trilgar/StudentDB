package com.stdb.dao.exam;

import com.stdb.entity.Exam;

public interface ExamDao {
    Exam create(Exam exam);

    Exam edit(Exam exam, int idExam);

    void delete(int idExam);

    Exam getById(int idExam);

}
