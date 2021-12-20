package ru.on8off.postgres.service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroupType;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@Builder
public class Filtering {
    private String name;
    private List<Integer> groupIds;
    private List<ElementGroupType> groupTypes;
    private Map<String, String> params;
    private List<String> tags;
}
