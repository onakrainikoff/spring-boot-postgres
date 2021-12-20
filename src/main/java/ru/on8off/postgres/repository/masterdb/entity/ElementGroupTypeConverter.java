package ru.on8off.postgres.repository.masterdb.entity;

import javax.persistence.AttributeConverter;

public class ElementGroupTypeConverter implements AttributeConverter<ElementGroupType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ElementGroupType attribute) {
        return attribute.getId();
    }

    @Override
    public ElementGroupType convertToEntityAttribute(Integer dbData) {
        return ElementGroupType.valueOf(dbData);
    }
}
