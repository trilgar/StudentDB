package com.stdb.service.user;

import com.stdb.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User editUser(User user, int idUser);

    void deleteUser(int idUser);

    List<User> getUsersByName(String name);
}
