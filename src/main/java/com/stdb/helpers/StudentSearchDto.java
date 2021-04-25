package com.stdb.helpers;

import lombok.Data;

import java.util.Map;

@Data
public class StudentSearchDto<T> {
    private T[] mainSearchCriteria;
    private Map<String, Object> filters;
}
