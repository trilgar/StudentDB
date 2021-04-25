package com.stdb.controllers;

import com.stdb.entity.Faculty;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.faculty.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping("/{idFaculty}")
    public Faculty editFaculty(@RequestBody Faculty faculty, @PathVariable int idFaculty) {
        return facultyService.editFaculty(faculty,idFaculty);
    }

    @DeleteMapping
    public ServerStatusResponse deleteFaculty(@RequestParam("id") int idFaculty){
        facultyService.deleteFaculty(idFaculty);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idFaculty}")
    public Faculty getFacultyById(@PathVariable int idFaculty){
        return facultyService.getById(idFaculty);
    }

    @GetMapping
    public Faculty getFacultyByName(@RequestParam("name") String name){
        return facultyService.getByName(name);
    }

    @GetMapping("/by_name")
    public List<Faculty> getFacultyByContainName(@RequestParam("name") String name){
        return facultyService.getByContainName(name);
    }
}

