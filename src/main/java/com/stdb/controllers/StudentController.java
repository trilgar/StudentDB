package com.stdb.controllers;

import com.stdb.entity.Student;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.helpers.StudentSearchDto;
import com.stdb.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{idStudent}")
    public Student editStudent(@RequestBody Student student, @PathVariable int idStudent) {
        return studentService.editStudent(student, idStudent);
    }

    @DeleteMapping
    public ServerStatusResponse deleteStudent(@RequestParam("id") int idStudent) {
        studentService.deleteStudent(idStudent);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idStudent}")
    public Student getStudentById(@PathVariable int idStudent) {
        return studentService.getById(idStudent);
    }

    @GetMapping
    public Student getStudentByName(@RequestParam("name") String name) {
        return studentService.getByName(name);
    }

    @PostMapping("/by_group")
    public List<Student> getStudentsByGroup(@RequestBody StudentSearchDto<String> ssd) {
        return studentService.getByGroup(ssd.getMainSearchCriteria(), ssd.getFilters());
    }

    @PostMapping("/by_course")
    public List<Student> getStudentsByCourse(@RequestBody StudentSearchDto<Integer> ssd) {
        return studentService.getByCourse(ssd.getMainSearchCriteria(), ssd.getFilters());
    }

    @GetMapping("by_disc_and_mark")
    public List<Student> getByDisciplineAndMark(@RequestParam("groups") String groups,
                                                @RequestParam("idDiscipline") int idDiscipline,
                                                @RequestParam("mark") int mark) {
        List<Integer> groupIds = Arrays.stream(groups.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return studentService.getByDisciplineAndMark(groupIds, idDiscipline, mark);
    }

}
