package com.stdb.dao.group;

import com.stdb.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group getById(int groupId) {
        String sql = "SELECT id,name,course,year " +
                "FROM groups WHERE id = ?";
        List<Group> groups = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, groupId),
                new GroupRowMapper());
        return groups.size() == 1 ? groups.get(0) : null;
    }

    @Override
    public Group createGroup(Group group) {
        String sql = "INSERT INTO groups (name,course,year) VALUES (?,?,?)";
        jdbcTemplate.update(
                sql,
                group.getName(),
                group.getCourse(),
                group.getYear()
        );
        return this.getByName(group.getName());
    }

    @Override
    public Group editGroup(Group group, int groupId) {
        String sql = "UPDATE groups SET name = ?, course = ?, year = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                group.getName(),
                group.getCourse(),
                group.getYear(),
                groupId
        );
        return this.getByName(group.getName());
    }

    @Override
    public void deleteGroup(int groupId) {
        String sql = "DELETE FROM groups WHERE id = ?";
        jdbcTemplate.update(
                sql,
                groupId
        );
    }

    @Override
    public Group getByName(String name) {
        String sql = "SELECT id,name,course,year " +
                "FROM groups WHERE name = ?";
        List<Group> groups = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new GroupRowMapper());
        return groups.size() == 1 ? groups.get(0) : null;
    }

    @Override
    public List<Group> getByContainName(String name) {
        String sql = "SELECT id,name,course,year " +
                "FROM groups WHERE name Like concat('%',?,'%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new GroupRowMapper());
    }
}
