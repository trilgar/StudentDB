package com.stdb.dao.user;

import com.stdb.entity.User;

import java.util.List;

public interface UserDao {
    User createUser(User user);

    User editUser(User user, int idUser);

    void deleteUser(int idUser);

    User getUserById(int idUser);

    List<User> getUsersByName(String name);

}
