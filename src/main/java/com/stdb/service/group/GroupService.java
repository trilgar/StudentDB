package com.stdb.service.group;

import com.stdb.entity.Group;

import java.util.List;

public interface GroupService {
    Group getById(int groupId);

    Group getByName(String name);

    Group createGroup(Group group);

    Group editGroup(Group group, int groupId);
    List<Group> getByContainName(String name);

    void deleteGroup(int groupId);
}
