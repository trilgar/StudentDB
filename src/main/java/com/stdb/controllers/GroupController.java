package com.stdb.controllers;

import com.stdb.entity.Group;
import com.stdb.helpers.ServerStatusResponse;
import com.stdb.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @PutMapping("/{idGroup}")
    public Group editGroup(@RequestBody Group group, @PathVariable int idGroup) {
        return groupService.editGroup(group, idGroup);
    }

    @DeleteMapping
    public ServerStatusResponse deleteGroup(@RequestParam("id") int idGroup) {
        groupService.deleteGroup(idGroup);
        return new ServerStatusResponse("Success");
    }

    @GetMapping("/{idGroup}")
    public Group getGroupById(@PathVariable int idGroup) {
        return groupService.getById(idGroup);
    }

    @GetMapping
    public Group getGroupByName(@RequestParam("name") String name) {
        return groupService.getByName(name);
    }

    @GetMapping("/by_name")
    public List<Group> getGroupByContainName(@RequestParam("name") String name) {
        return groupService.getByContainName(name);
    }
}
