package com.stdb.dao.group;

import com.stdb.entity.Group;

import java.util.List;

public interface GroupDao {
    Group getById(int groupId);

    Group createGroup(Group group);

    Group editGroup(Group group, int groupId);

    void deleteGroup(int groupId);

    Group getByName(String name);

    List<Group> getByContainName(String name);
}
