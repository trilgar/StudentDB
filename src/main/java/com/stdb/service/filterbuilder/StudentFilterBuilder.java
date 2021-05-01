package com.stdb.service.filterbuilder;

import com.stdb.entity.TeacherCategory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class StudentFilterBuilder {
    private static final String GENDER_FILTER = "gender";
    private static final String NAME_FILTER = "name";
    private static final String AGE_FILTER = "age";
    private static final String YEAR_FILTER = "year";
    private static final String KIDS_FILTER = "kids";
    private static final String STIPENDIUM_FILTER = "stipendium";


    public static String buildFilter(Map<String, Object> filters) {
        StringBuilder sql = new StringBuilder();

        if (filters.isEmpty()) {
            return "";
        }

        for (Map.Entry<String, ?> filter : filters.entrySet()) {
            Map<String, Integer> stdFilter;
            switch (filter.getKey()) {
                case GENDER_FILTER:
                    sql.append(" AND std.gender = \'").append(filter.getValue().toString()).append("\'");
                    break;
                case NAME_FILTER:
                    sql.append(" AND std.name LIKE '%").append(filter.getValue().toString()).append("%'");
                    break;
                case AGE_FILTER:
                    log.debug("Entered stdFilter");
                    stdFilter = (Map<String, Integer>) (filter.getValue());
                    log.debug("Succeed stdFilter cast");
                    sql.append(" AND (std.age BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case YEAR_FILTER:
                    stdFilter = (Map<String, Integer>) filter.getValue();
                    sql.append(" AND (grp.year BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case KIDS_FILTER:
                    sql.append(" AND std.kids > 0");
                    break;
                case STIPENDIUM_FILTER:
                    stdFilter = (Map<String, Integer>) filter.getValue();
                    sql.append(" AND (std.stipendium BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
            }
        }
        return sql.substring(5);
    }

    public static String getFilterByGroup(List<Integer> groupIds) {
        StringBuilder sql = new StringBuilder("WHERE s.id_group IN(");
        for(int groupId: groupIds){
            sql.append(groupId).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }
}
