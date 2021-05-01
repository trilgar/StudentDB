package com.stdb.service.filterbuilder;

import com.stdb.entity.TeacherCategory;

import java.util.List;
import java.util.Map;

public class MainFilterBuilder {
    public static String getTeacherFilter(Map<String, Object> filters) {
        final String GENDER_FILTER = "gender";
        final String NAME_FILTER = "name";
        final String AGE_FILTER = "age";
        final String YEAR_FILTER = "year";
        final String KIDS_FILTER = "kids";
        final String WAGE_FILTER = "wage";
        final String ASP_FILTER = "isAsp";
        final String SW_FILTER = "sw";
        final String CATEGORY = "category";


        StringBuilder sql = new StringBuilder();
        if (filters.isEmpty()) {
            return "";
        }

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            Map<String, Integer> stdFilter;
            switch (filter.getKey()) {
                case GENDER_FILTER:
                    sql.append(" AND tcr.gender = '").append(filter.getValue().toString()).append("'");
                    break;
                case NAME_FILTER:
                    sql.append(" AND tcr.name LIKE '%").append(filter.getValue().toString()).append("%'");
                    break;
                case AGE_FILTER:
                    stdFilter = (Map<String, Integer>) (filter.getValue());
                    sql.append(" AND (tcr.age BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case YEAR_FILTER:
                    stdFilter = (Map<String, Integer>) filter.getValue();
                    sql.append(" AND (tcr.year BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case KIDS_FILTER:
                    stdFilter = (Map<String, Integer>) filter.getValue();
                    sql.append(" AND (tcr.kids BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case WAGE_FILTER:
                    stdFilter = (Map<String, Integer>) filter.getValue();
                    sql.append(" AND (tcr.wage BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case ASP_FILTER:
                    sql.append(" AND tcr.is_asp = 'TRUE'");
                    break;
                case SW_FILTER:
                    stdFilter = (Map<String, Integer>) filter.getValue();
                    sql.append(" AND (sw.year BETWEEN ").append(stdFilter.get("from")).append(" AND ").append(stdFilter.get("to")).append(")");
                    break;
                case CATEGORY:
                    sql.append(" AND (");
                    for (String category : (List<String>) filter.getValue()){
                        sql.append(" tcr.category = '").append(category).append("'").append(" OR");
                    }
                    sql.delete(sql.length() - 3, sql.length());
                    sql.append(")");
                    break;
            }
        }
        return sql.substring(5);

    }
    public static String getTeacherCategoryFilter(List<TeacherCategory> teacherCategories){
        StringBuilder sql = new StringBuilder("WHERE t.category IN(");
        for(TeacherCategory teacherCategory: teacherCategories){
            sql.append("'").append(teacherCategory.toString()).append("'").append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }


}
