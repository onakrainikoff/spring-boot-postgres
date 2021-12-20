package ru.on8off.postgres.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Paging {
    private Integer number = 0;
    private Integer size = Integer.MAX_VALUE;
}
