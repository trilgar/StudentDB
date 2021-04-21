package com.stdb.dao.user;

import com.stdb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (email, password, name) " +
                "VALUES (?,?,?)";
        jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getPassword(),
                user.getName());
        List<User> users = this.getUsersByName(user.getName());
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }

    }

    @Override
    public User editUser(User user, int idUser) {
        String sql = "UPDATE users SET email = ?, password = ?, name = ? " +
                "WHERE users.id = ?";
        jdbcTemplate.update(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, user.getEmail());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getName());
                    preparedStatement.setInt(4, idUser);
                }
        );
        return this.getUserById(idUser);
    }

    @Override
    public void deleteUser(int idUser) {
        String sql = "DELETE FROM users WHERE users.id = ?";
        jdbcTemplate.update(
                sql,
                idUser
        );
    }

    @Override
    public User getUserById(int idUser) {
        String sql = "SELECT id, email, password, name FROM users " +
                "WHERE id = ?";
        List<User> users = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idUser),
                new UserRowMapper()
        );
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getUsersByName(String name) {
        String sql = "SELECT id, email, password, name FROM users " +
                "WHERE name LIKE concat('%',?,'%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new UserRowMapper()
        );
    }
}
