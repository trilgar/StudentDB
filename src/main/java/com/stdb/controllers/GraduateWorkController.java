package com.stdb.controllers;

import com.stdb.entity.CombinedGW;
import com.stdb.entity.GraduateWork;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.graduatework.GraduateWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/graduate_works")
@CrossOrigin(origins = "${frontend.ulr}")
public class GraduateWorkController {
    private final GraduateWorkService graduateWorkService;

    @PostMapping
    public GraduateWork createGw(@RequestBody GraduateWork graduateWork) {
        return graduateWorkService.create(graduateWork);
    }

    @PutMapping("/{idGw}")
    public GraduateWork editGw(@RequestBody GraduateWork graduateWork, @PathVariable int idGw) {
        return graduateWorkService.edit(graduateWork, idGw);
    }

    @DeleteMapping
    public ServerStatusResponse deleteGw(@RequestParam("id") int id) {
        graduateWorkService.delete(id);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idGw}")
    public GraduateWork getGwById(@PathVariable int idGw) {
        return graduateWorkService.getById(idGw);
    }

    @GetMapping("/by_name")
    public List<GraduateWork> getGwByName(@RequestParam("name") String name) {
        return graduateWorkService.getByContainName(name);
    }

    @GetMapping("by_cathedra")
    public List<CombinedGW> getByCathedra(@RequestParam("idCathedra") int idCathedra) {
        return graduateWorkService.getByCathedra(idCathedra);
    }

    @GetMapping("by_teacher")
    public List<CombinedGW> getByTeacher(@RequestParam("idTeacher") int idTeacher) {
        return graduateWorkService.getByTeacher(idTeacher);
    }
}
