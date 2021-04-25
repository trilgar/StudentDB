package com.stdb.controllers;

import com.stdb.entity.Teacher;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.helpers.StudentSearchDto;
import com.stdb.service.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.create(teacher);
    }

    @PutMapping("/{idTeacher}")
    public Teacher editTeacher(@RequestBody Teacher teacher, @PathVariable int idTeacher) {
        return teacherService.edit(teacher, idTeacher);
    }

    @DeleteMapping
    public ServerStatusResponse deleteTeacher(@RequestParam("id") int idTeacher) {
        teacherService.delete(idTeacher);
        return new ServerStatusResponse("Success");
    }

    @GetMapping
    public Teacher getTeacherByName(@RequestParam("name") String name) {
        return teacherService.getByName(name);
    }

    @PostMapping("/by_faculty")
    public List<Teacher> getTeachersByFaculty(@RequestBody StudentSearchDto<String> ssd) {
        return teacherService.getByFaculty(ssd.getMainSearchCriteria(), ssd.getFilters());
    }

    @PostMapping("/by_cathedra")
    public List<Teacher> getTeachersByCathedra(@RequestBody StudentSearchDto<String> ssd) {
        return teacherService.getByCathedra(ssd.getMainSearchCriteria(), ssd.getFilters());
    }
}
