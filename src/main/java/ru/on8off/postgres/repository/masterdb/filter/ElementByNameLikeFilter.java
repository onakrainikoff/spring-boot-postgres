package ru.on8off.postgres.repository.masterdb.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.on8off.postgres.repository.masterdb.entity.Element;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ElementByNameLikeFilter implements Specification<Element> {
    private String namePattern;

    public ElementByNameLikeFilter(String namePattern) {
        this.namePattern = namePattern;
    }

    @Override
    public Predicate toPredicate(Root<Element> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(namePattern == null || namePattern.isEmpty()) {
            return null;
        }
        return cb.like(root.<String>get("name"), namePattern);
    }
}
