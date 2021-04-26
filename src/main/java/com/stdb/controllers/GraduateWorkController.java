package com.stdb.controllers;

import com.stdb.entity.GraduateWork;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.graduatework.GraduateWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/graduate_works")
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

    @GetMapping
    public GraduateWork getGwByName(@RequestParam("name") String name) {
        return graduateWorkService.getByName(name);
    }
}
