package ru.on8off.postgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.postgres.repository.masterdb.ElementRepository;
import ru.on8off.postgres.repository.masterdb.entity.Element;
import ru.on8off.postgres.repository.masterdb.filter.ElementByGroupFilter;
import ru.on8off.postgres.repository.masterdb.filter.ElementByNameLikeFilter;
import ru.on8off.postgres.service.domain.Filtering;
import ru.on8off.postgres.service.domain.Paging;
import ru.on8off.postgres.service.domain.Sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FindElementsService {
    @Autowired
    private ElementRepository elementRepository;

    @Transactional(readOnly = true)
    public Page<Element> findElements(
            Filtering filtering,
            Paging paging,
            Sorting sorting
    ){
        var filters = getFilters(filtering);
        var pageRequest = getPageRequest(paging, sorting);
        return elementRepository.findAll(filters, pageRequest);
    }

    private Specification<Element> getFilters(Filtering filtering){
        if(filtering == null){
            return null;
        }
        List<Specification<Element>> filters = new ArrayList<>();
        if(filtering.getName() != null && !filtering.getName().isEmpty()) {
            filters.add(new ElementByNameLikeFilter(filtering.getName()));
        }
        if(filtering.getGroupIds() != null && !filtering.getGroupIds().isEmpty() ||
                filtering.getGroupTypes() != null && !filtering.getGroupTypes().isEmpty()){
            filters.add(new ElementByGroupFilter(filtering.getGroupIds(), filtering.getGroupTypes()));
        }
        return filters.stream().reduce(Specification::and).orElse(null);

    }

    private PageRequest getPageRequest(Paging paging, Sorting sorting){
        paging = Objects.requireNonNullElse(paging, new Paging());
        return PageRequest.of(paging.getNumber(), paging.getSize(), getSort(sorting));
    }

    private Sort getSort(Sorting sorting){
        if(sorting == null || sorting.getSorts().isEmpty()) {
            return Sort.unsorted();
        }
        return Sort.by(
                sorting.getSorts().entrySet().stream()
                        .map(e -> e.getValue() ? Sort.Order.asc(e.getKey()) : Sort.Order.desc(e.getKey()))
                        .collect(Collectors.toList())
        );
    }

}
