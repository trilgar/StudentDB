package com.stdb.service.exam;

import com.stdb.entity.Exam;

public interface ExamService {
    Exam create(Exam exam);

    Exam edit(Exam exam, int idExam);

    void delete(int idExam);

    Exam getById(int idExam);
}
