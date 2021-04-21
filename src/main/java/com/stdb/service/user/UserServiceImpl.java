package com.stdb.service.user;

import com.stdb.dao.user.UserDao;
import com.stdb.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public User editUser(User user, int idUser) {
        return userDao.editUser(user, idUser);
    }

    @Override
    public void deleteUser(int idUser) {
        userDao.deleteUser(idUser);
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userDao.getUsersByName(name);
    }
}
