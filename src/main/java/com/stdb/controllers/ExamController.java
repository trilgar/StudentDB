package com.stdb.controllers;

import com.stdb.entity.Exam;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.exam.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exams")
@CrossOrigin(origins = "${frontend.ulr}")
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

    @GetMapping("/by_name")
    public List<Exam> getByDescription(@RequestParam("name") String name) {
        return examService.getByName(name);
    }

    @GetMapping("/validate")
    public Exam getIds(@RequestParam int idDiscipline,
                       @RequestParam int idStudent) {
        return examService.getIds(idDiscipline, idStudent);
    }

    @GetMapping("/{idExam}")
    public Exam getExamById(@PathVariable int idExam) {
        return examService.getById(idExam);
    }
}
