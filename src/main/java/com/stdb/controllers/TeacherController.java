package com.stdb.controllers;

import com.stdb.entity.Teacher;
import com.stdb.entity.TeacherCategory;
import com.stdb.helpers.IntervalFilter;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.helpers.StudentSearchDto;
import com.stdb.service.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/by_group")
    public List<Teacher> getByGroup(@RequestParam("dName") String dName,
                                    @RequestParam("idGroup") int idGroup,
                                    @RequestParam("idFaculty") int idFaculty) {
        return teacherService.getByGroup(dName, idGroup, idFaculty);
    }

    @GetMapping("/by_course")
    public List<Teacher> getByCourse(@RequestParam("dName") String dName,
                                     @RequestParam("course") int course,
                                     @RequestParam("idFaculty") int idFaculty) {
        return teacherService.getByCourse(dName, course, idFaculty);
    }

    @GetMapping("by_category_group")
    public List<Teacher> getByCategoryGroup(@RequestParam("categories") String categories,
                                            @RequestParam("idGroup") int idGroup,
                                            @RequestParam("idFaculty") int idFaculty,
                                            @RequestParam("from") int from,
                                            @RequestParam("to") int to) {
        List<TeacherCategory> teacherCategories = Arrays.stream(categories.split(","))
                .map(TeacherCategory::valueOf)
                .collect(Collectors.toList());
        IntervalFilter semester = new IntervalFilter(from, to);
        return teacherService.getByCategoryGroup(teacherCategories, idGroup, idFaculty, semester);
    }

    @GetMapping("by_category_course")
    public List<Teacher> getByCategoryCourse(@RequestParam("categories") String categories,
                                             @RequestParam("course") int course,
                                             @RequestParam("idFaculty") int idFaculty,
                                             @RequestParam("from") int from,
                                             @RequestParam("to") int to) {
        List<TeacherCategory> teacherCategories = Arrays.stream(categories.split(","))
                .map(TeacherCategory::valueOf)
                .collect(Collectors.toList());

        IntervalFilter semester = new IntervalFilter(from, to);
        return teacherService.getByCategoryCourse(teacherCategories, course, idFaculty, semester);
    }

    @GetMapping("by_exams")
    public List<Teacher> getByExams(@RequestParam("idGroup") int idGroup,
                                    @RequestParam("dName") String dName,
                                    @RequestParam("semester") int semester) {
        return teacherService.getByExams(idGroup, dName, semester);
    }
}
