package ru.on8off.postgres.repository.masterdb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "element_groups")
@Getter
@Setter
public class ElementGroup extends BaseEntity<Integer>{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "group_type", nullable = false)
    @Convert(converter = ElementGroupTypeConverter.class)
    private ElementGroupType type;
}
