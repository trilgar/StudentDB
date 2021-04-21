package com.stdb.controllers;

import com.stdb.helpers.ServerStatusResponse;
import com.stdb.entity.User;
import com.stdb.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> findUserByName(@RequestParam("name") String userName) {
        return userService.getUsersByName(userName);
    }

    @PutMapping("/{idUser}")
    public User editUser(@PathVariable int idUser,
                         @RequestBody User user) {
        return userService.editUser(user, idUser);
    }

    @DeleteMapping("/{idUser}")
    public ServerStatusResponse deleteUser(@PathVariable int idUser) {
        userService.deleteUser(idUser);
        return new ServerStatusResponse("user deleted successfully");
    }
}
