package com.stdb.controllers;

import com.stdb.entity.Student;
import com.stdb.helpers.IntervalFilter;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.helpers.StudentSearchDto;
import com.stdb.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "${frontend.ulr}")
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

    @GetMapping("by_name")
    public List<Student> getByContainingName(@RequestParam("name") String name){
        return studentService.getByContainingName(name);
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

    @GetMapping("by_grp_mrk")
    public List<Student> getByGroupAndMarks(@RequestParam("groups") String groups,
                                            @RequestParam("idFaculty") int idFaculty,
                                            @RequestParam("mark") int mark,
                                            @RequestParam("semester") int semester) {
        List<Integer> groupIds = Arrays.stream(groups.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return studentService.getByGroupAndMarks(groupIds, idFaculty, mark, semester);
    }

    @GetMapping("by_crs_mrk")
    public List<Student> getByCourseAndMarks(@RequestParam("course") int course,
                                             @RequestParam("idFaculty") int idFaculty,
                                             @RequestParam("mark") int mark,
                                             @RequestParam("semester") int semester) {
        return studentService.getByCourseAndMarks(course, idFaculty, mark, semester);
    }

    @GetMapping("by_group_and_semester")
    public List<Student> getByGroupAndSemester(@RequestParam("groups") String groups,
                                               @RequestParam("from") int from,
                                               @RequestParam("to") int to) {
        List<Integer> groupIds = Arrays.stream(groups.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        IntervalFilter semester = new IntervalFilter(from, to);
        return studentService.getByGroupAndSemester(groupIds, semester);
    }

    @GetMapping("by_mark_and_semester")
    public List<Student> getByMarkAndSemester(@RequestParam("mark") int mark,
                                              @RequestParam("idDiscipline") int idDiscipline,
                                              @RequestParam("from") int from,
                                              @RequestParam("to") int to) {
        IntervalFilter semester = new IntervalFilter(from, to);
        return studentService.getByMarkAndSemester(mark, idDiscipline, semester);
    }

}
