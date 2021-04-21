package com.stdb.service.group;

import com.stdb.dao.group.GroupDao;
import com.stdb.entity.Group;
import com.stdb.helpers.exceptions.ForeignKeyViolationException;
import com.stdb.helpers.exceptions.NameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupDao groupDao;

    @Override
    public Group getById(int groupId) {
        return groupDao.getById(groupId);
    }

    @Override
    public Group getByName(String name) {
        return groupDao.getByName(name);
    }

    @Override
    public List<Group> getByContainName(String name) {
        return groupDao.getByContainName(name);
    }

    @Override
    public Group createGroup(Group group) {
        Group newGroup;
        try{
            newGroup = groupDao.createGroup(group);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
        return newGroup;
    }

    @Override
    public Group editGroup(Group group, int groupId) {
        Group newGroup;
        try{
            newGroup = groupDao.editGroup(group, groupId);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new NameAlreadyExistsException();
        }
        return newGroup;
    }

    @Override
    public void deleteGroup(int groupId) {
        try{
            groupDao.deleteGroup(groupId);
        }
        catch (DataIntegrityViolationException ex){
            log.error(ex.getMessage());
            throw new ForeignKeyViolationException();
        }
    }
}
