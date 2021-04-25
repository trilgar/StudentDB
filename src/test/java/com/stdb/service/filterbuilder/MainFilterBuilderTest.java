package com.stdb.service.filterbuilder;

import com.stdb.entity.Gender;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainFilterBuilderTest {

    @Test
    void getTeacherFilter() {
        Map<String, Object> filters = new LinkedHashMap<>();
        filters.put("gender", Gender.MALE);
        Map<String, Integer> interval1 = new HashMap<>();
        interval1.put("from", 11);
        interval1.put("to", 12);
        filters.put("age", interval1);

        Map<String, Integer> interval2 = new HashMap<>();
        interval2.put("from", 1);
        interval2.put("to", 3);
        filters.put("year", interval2);
        filters.put("kids", interval2);

        Map<String, Integer> interval3 = new HashMap<>();
        interval3.put("from", 100);
        interval3.put("to", 200);
        filters.put("wage", interval3);

        filters.put("isAsp", null);

        Map<String, Integer> interval4 = new HashMap<>();
        interval4.put("from", 2000);
        interval4.put("to", 2001);
        filters.put("sw", interval4);

        List<String> categories = new ArrayList<>();
        categories.add("Assistant");
        categories.add("Lecturer");
        filters.put("category", categories);
        assertEquals("tcr.gender = 'MALE' AND (tcr.age BETWEEN 11 AND 12) AND (tcr.year BETWEEN 1 AND 3) AND " +
                        "(tcr.kids BETWEEN 1 AND 3) AND (tcr.wage BETWEEN 100 AND 200) AND tcr.is_asp = TRUE AND (sw.year BETWEEN 2000 AND 2001) AND ( tcr.category = Assistant OR tcr.category = Lecturer)",
                MainFilterBuilder.getTeacherFilter(filters));
    }
}