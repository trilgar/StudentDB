package com.stdb.dao.faculty;

import com.stdb.entity.Faculty;

import java.util.List;

public interface FacultyDao {
    Faculty createFaculty(Faculty faculty);

    Faculty editFaculty(Faculty faculty, int idFaculty);

    void deleteFaculty(int idFaculty);

    Faculty getById(int idFaculty);

    Faculty getByName(String name);

    List<Faculty> getByContainName(String name);
}
