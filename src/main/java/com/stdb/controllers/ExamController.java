package com.stdb.controllers;

import com.stdb.entity.Exam;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.exam.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exams")
public class ExamController {
    private final ExamService examService;

    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.create(exam);
    }

    @PutMapping("/{idExam}")
    public Exam editExam(@RequestBody Exam exam, @PathVariable int idExam) {
        return examService.edit(exam, idExam);
    }

    @DeleteMapping
    public ServerStatusResponse editExam(@RequestParam("id") int idExam) {
        examService.delete(idExam);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idExam}")
    public Exam getExamById(@PathVariable int idExam) {
        return examService.getById(idExam);
    }
}
