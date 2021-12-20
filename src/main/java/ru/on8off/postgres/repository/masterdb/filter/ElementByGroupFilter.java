package ru.on8off.postgres.repository.masterdb.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.on8off.postgres.repository.masterdb.entity.Element;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroup;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroupType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ElementByGroupFilter implements Specification<Element> {
    private List<Integer> groupIds;
    private List<ElementGroupType> types;

    public ElementByGroupFilter(List<Integer> groupIds, List<ElementGroupType> types) {
        this.groupIds = groupIds;
        this.types = types;
    }

    @Override
    public Predicate toPredicate(Root<Element> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = null;
        Join<Element, ElementGroup> groupJoin = root.join("elementGroup", JoinType.LEFT);
        if(groupIds != null && !groupIds.isEmpty()){
            predicate = groupJoin.<Integer>get("id").in(groupIds);
        }
        if(types != null && !types.isEmpty()){
            if (predicate == null) {
                predicate = groupJoin.<ElementGroupType>get("type").in(types);
            } else {
                predicate = cb.and(predicate, groupJoin.<ElementGroupType>get("type").in(types));
            }
        }
        return predicate;
    }
}
