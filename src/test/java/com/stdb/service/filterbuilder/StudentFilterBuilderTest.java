package com.stdb.service.filterbuilder;

import com.stdb.entity.Gender;
import com.stdb.helpers.IntervalFilter;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentFilterBuilderTest {

    @Test
    void buildFilter() {
        Map<String, Object> filters = new LinkedHashMap<>();
        filters.put("gender", Gender.MALE);
        filters.put("age", new IntervalFilter(11, 12));
        filters.put("year", new IntervalFilter(1, 3));
        filters.put("kids", 1);
        filters.put("stipendium", new IntervalFilter(100, 200));
        assertEquals("std.gender = MALE AND (std.age BETWEEN 11 AND 12) AND (grp.year BETWEEN 1 AND 3) AND std.kids > 0 AND (std.stipendium BETWEEN 100 AND 200)",
                StudentFilterBuilder.buildFilter(filters));
    }
}