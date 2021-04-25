package com.stdb.controllers;

import com.stdb.entity.ScienceWork;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.sciencework.ScienceWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/science_works")
public class ScienceWorkController {
    private final ScienceWorkService scienceWorkService;

    @PostMapping
    public ScienceWork createScienceWork(@RequestBody ScienceWork scienceWork) {
        return scienceWorkService.create(scienceWork);
    }

    @PutMapping("/{idSw}")
    public ScienceWork editScienceWork(@RequestBody ScienceWork scienceWork, @PathVariable int idSw) {
        return scienceWorkService.edit(scienceWork, idSw);
    }

    @DeleteMapping
    public ServerStatusResponse deleteScienceWork(@RequestParam("id") int idScienceWork) {
        scienceWorkService.delete(idScienceWork);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idSw}")
    public ScienceWork getSwById(@PathVariable int idSw) {
        return scienceWorkService.getById(idSw);
    }

    @GetMapping
    public ScienceWork getSwById(@RequestParam("name") String name) {
        return scienceWorkService.getByName(name);
    }

    @GetMapping("/by_filters")
    public List<ScienceWork> getSwByItems(@RequestParam("cathedra") int idCathedra, @RequestParam("faculty") int idFaculty) {
        return scienceWorkService.getWorksByItems(idFaculty, idCathedra);
    }
}
