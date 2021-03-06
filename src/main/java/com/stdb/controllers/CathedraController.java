package com.stdb.controllers;


import com.stdb.entity.Cathedra;
import com.stdb.helpers.IntervalFilter;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.cathedra.CathedraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cathedras")
@RequiredArgsConstructor
@CrossOrigin(origins = "${frontend.ulr}")
public class CathedraController {
    private final CathedraService cathedraService;

    @PostMapping
    public Cathedra createCathedra(@RequestBody Cathedra cathedra) {
        return cathedraService.createCathedra(cathedra);
    }

    @PutMapping("/{idCathedra}")
    public Cathedra editCathedra(@RequestBody Cathedra cathedra, @PathVariable int idCathedra) {
        return cathedraService.editCathedra(cathedra, idCathedra);
    }

    @DeleteMapping
    public ServerStatusResponse deleteCathedra(@RequestParam("id") int idCathedra) {
        cathedraService.deleteCathedra(idCathedra);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idCathedra}")
    public Cathedra getCathedraById(@PathVariable int idCathedra) {
        return cathedraService.getById(idCathedra);
    }

    @GetMapping
    public Cathedra getCathedraByName(@RequestParam("name") String name) {
        return cathedraService.getByName(name);
    }

    @GetMapping("/by_name")
    public List<Cathedra> getCathedraByContainName(@RequestParam("name") String name) {
        return cathedraService.getByContainName(name);
    }

    @GetMapping("/by_group")
    public List<Cathedra> getCByGroupID(@RequestParam("idGroup") int idGroup,
                                        @RequestParam("idFaculty") int idFaculty,
                                        @RequestParam("semFrom") int from,
                                        @RequestParam("semTo") int to) {
        return cathedraService.getByGroup(idGroup, idFaculty, new IntervalFilter(from, to));
    }

    @GetMapping("/by_course")
    public List<Cathedra> getCByCourseID(@RequestParam("idCourse") int idCourse,
                                         @RequestParam("idFaculty") int idFaculty,
                                         @RequestParam("semFrom") int from,
                                         @RequestParam("semTo") int to) {
        return cathedraService.getByCourse(idCourse, idFaculty, new IntervalFilter(from, to));
    }
}
