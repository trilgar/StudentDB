package com.stdb.dao.discipline;

import com.stdb.entity.DType;
import com.stdb.entity.Discipline;
import com.stdb.entity.DisciplineLoad;
import com.stdb.entity.TypeToHours;
import com.stdb.service.filterbuilder.StudentFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DisciplineDaoImpl implements DisciplineDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DisciplineDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Discipline create(Discipline discipline) {
        String sql = "INSERT INTO discipline (type, id_teacher, id_group, name, hours, course, semester) " +
                "VALUES (?::d_type, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                discipline.getType().toString(),
                discipline.getIdTeacher(),
                discipline.getIdGroup(),
                discipline.getName(),
                discipline.getHours(),
                discipline.getCourse(),
                discipline.getSemester()
        );
        return this.getByItems(discipline);
    }

    @Override
    public Discipline edit(Discipline discipline, int idDiscipline) {
        String sql = "UPDATE discipline SET type = ?::d_type, id_teacher = ?, " +
                "id_group = ?, name = ?, hours = ?, course = ?, semester = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                discipline.getType().toString(),
                discipline.getIdTeacher(),
                discipline.getIdGroup(),
                discipline.getName(),
                discipline.getHours(),
                discipline.getCourse(),
                discipline.getSemester(),
                idDiscipline
        );
        return this.getByItems(discipline);
    }

    @Override
    public void delete(int idDiscipline) {
        String sql = "DELETE FROM discipline WHERE id = ?";
        jdbcTemplate.update(sql, idDiscipline);
    }

    @Override
    public Discipline getById(int idDiscipline) {
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester " +
                "FROM discipline WHERE id = ?";
        List<Discipline> disciplines = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idDiscipline),
                new DisciplineRowMapper()
        );
        return disciplines.size() == 1 ? disciplines.get(0) : null;
    }

    @Override
    public Discipline getByItems(Discipline discipline) {
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester " +
                "FROM discipline WHERE type = ?::d_type AND id_teacher = ? AND id_group = ? AND name = ? AND hours = ? AND course = ? AND semester = ?";
        List<Discipline> disciplines = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, discipline.getType().toString());
                    preparedStatement.setInt(2, discipline.getIdTeacher());
                    preparedStatement.setInt(3, discipline.getIdGroup());
                    preparedStatement.setString(4, discipline.getName());
                    preparedStatement.setInt(5, discipline.getHours());
                    preparedStatement.setInt(6, discipline.getCourse());
                    preparedStatement.setInt(7, discipline.getSemester());

                },
                new DisciplineRowMapper()
        );
        return disciplines.size() == 1 ? disciplines.get(0) : null;
    }

    @Override
    public List<Discipline> getByName(String name) {
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester " +
                "FROM discipline WHERE name LIKE concat('%', ?, '%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new DisciplineRowMapper()
        );
    }

    @Override
    public List<DisciplineLoad> getTeachersLoad(int semester, int idTeacher) {
        jdbcTemplate.update("TRUNCATE summary");
        String sql = "INSERT INTO summary (name, type, hours) " +
                "(SELECT name, type, sum(hours) as hours " +
                "           FROM discipline " +
                "           WHERE semester = ? AND id_teacher = ?" +
                "           GROUP BY type, name )";
        jdbcTemplate.update(
                sql,
                semester,
                idTeacher);
        sql = "SELECT DISTINCT name FROM summary";
        List<String> disciplineNames = jdbcTemplate.query(
                sql,
                (resultSet, i) -> resultSet.getString("name")
        );
        sql = "SELECT type, hours FROM summary WHERE name = ?";

        List<DisciplineLoad> disciplineLoads = new ArrayList<>();
        for (String discipline : disciplineNames) {
            List<TypeToHours> entries = jdbcTemplate.query(
                    sql,
                    preparedStatement -> preparedStatement.setString(1, discipline),
                    (resultSet, i) -> new TypeToHours(DType.valueOf(resultSet.getString("type")), resultSet.getInt("hours"))
            );
            disciplineLoads.add(this.toDisciplineLoad(discipline, entries));

        }
        return disciplineLoads;

    }

    @Override
    public List<DisciplineLoad> getLoadByCathedra(int semester, int idCathedra) {
        jdbcTemplate.update("TRUNCATE summary");
        String sql = "INSERT INTO summary (name, type, hours) " +
                "(SELECT discipline.name, type, sum(hours) as hours " +
                "           FROM discipline " +
                "           INNER JOIN teachers t on t.id = discipline.id_teacher " +
                "           WHERE semester = ? AND id_cathedra = ?" +
                "           GROUP BY type, discipline.name )";
        jdbcTemplate.update(
                sql,
                semester,
                idCathedra);
        sql = "SELECT DISTINCT name FROM summary";
        List<String> disciplineNames = jdbcTemplate.query(
                sql,
                (resultSet, i) -> resultSet.getString("name")
        );
        sql = "SELECT type, hours FROM summary WHERE name = ?";

        List<DisciplineLoad> disciplineLoads = new ArrayList<>();
        for (String discipline : disciplineNames) {
            List<TypeToHours> entries = jdbcTemplate.query(
                    sql,
                    preparedStatement -> preparedStatement.setString(1, discipline),
                    (resultSet, i) -> new TypeToHours(DType.valueOf(resultSet.getString("type")), resultSet.getInt("hours"))
            );
            disciplineLoads.add(this.toDisciplineLoad(discipline, entries));

        }
        return disciplineLoads;
    }

    private DisciplineLoad toDisciplineLoad(String name, List<TypeToHours> entries) {
        DisciplineLoad disciplineLoad = new DisciplineLoad();
        for (TypeToHours entry : entries) {
            switch (entry.getType()) {
                case Lection: {
                    disciplineLoad.setLectionHours(entry.getHours());
                    break;
                }
                case Seminar: {
                    disciplineLoad.setSeminarHours(entry.getHours());
                    break;
                }
                case LabWork: {
                    disciplineLoad.setLabWorkHours(entry.getHours());
                    break;
                }
                case Consultancy: {
                    disciplineLoad.setConsultancyHours(entry.getHours());
                    break;
                }
                case CourseWork: {
                    disciplineLoad.setCourseworkHours(entry.getHours());
                    break;
                }

            }
        }
        disciplineLoad.setDisciplineName(name);
        return disciplineLoad;

    }

    @Override
    public List<Discipline> getByGroups(List<Integer> groups) {
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester FROM discipline s ";
        sql += StudentFilterBuilder.getFilterByGroup(groups);
        return jdbcTemplate.query(
                sql,
                new DisciplineRowMapper()
        );
    }

    @Override
    public List<Discipline> getByCourses(List<Integer> courses) {
        StringBuilder coursesString = new StringBuilder();
        if(!courses.isEmpty()){
            coursesString = new StringBuilder("WHERE d.course IN (");
            StringBuilder finalCoursesString = coursesString;
            courses.forEach((course) -> finalCoursesString.append(course).append(","));
            coursesString.deleteCharAt(coursesString.length() - 1);
            coursesString.append(")");
        }
        String sql = "SELECT id, type, id_teacher, id_group, name, hours, course, semester FROM discipline d ";
        sql += coursesString.toString();
        return jdbcTemplate.query(
                sql,
                new DisciplineRowMapper()
        );
    }
}
