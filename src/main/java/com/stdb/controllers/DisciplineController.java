package com.stdb.controllers;

import com.stdb.entity.Discipline;
import com.stdb.entity.DisciplineLoad;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.discipline.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "${frontend.ulr}")
@RequestMapping("/api/disciplines")
public class DisciplineController {
    private final DisciplineService disciplineService;

    @PostMapping
    public Discipline createDiscipline(@RequestBody Discipline discipline) {
        return disciplineService.create(discipline);
    }

    @PutMapping("/{idDiscipline}")
    public Discipline editDiscipline(@RequestBody Discipline discipline, @PathVariable int idDiscipline) {
        return disciplineService.edit(discipline, idDiscipline);
    }

    @DeleteMapping
    public ServerStatusResponse deleteDiscipline(@RequestParam("id") int idDiscipline) {
        disciplineService.delete(idDiscipline);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idDiscipline}")
    public Discipline getDisciplineById(@PathVariable int idDiscipline) {
        return disciplineService.getById(idDiscipline);
    }

    @GetMapping("load/by_teacher")
    public List<DisciplineLoad> getLoadByTeachersId(@RequestParam("semester") int semester,
                                                    @RequestParam("idTeacher") int idTeacher) {
        return disciplineService.getTeachersLoad(semester, idTeacher);
    }

    @GetMapping("load/by_cathedra")
    public List<DisciplineLoad> getLoadByCathedraId(@RequestParam("semester") int semester,
                                                    @RequestParam("cathedra") int cathedra) {
        return disciplineService.getLoadByCathedra(semester, cathedra);
    }

}
