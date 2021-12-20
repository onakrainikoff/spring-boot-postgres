package ru.on8off.postgres.repository.masterdb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "counters")
@Getter @Setter
public class Counter extends BaseEntity<Integer>{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "date_last_modified", nullable = false)
    protected ZonedDateTime dateLastModified;
}
