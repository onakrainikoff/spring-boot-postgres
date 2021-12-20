package ru.on8off.postgres.service.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@ToString
@Getter
public class Sorting {
    private Map<String, Boolean> sorts = new LinkedHashMap<>();

    public Sorting add(String column, boolean asc){
        sorts.put(column, asc);
        return this;
    }
}
