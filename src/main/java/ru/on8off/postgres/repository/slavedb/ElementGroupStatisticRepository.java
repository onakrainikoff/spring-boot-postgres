package ru.on8off.postgres.repository.slavedb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.on8off.postgres.repository.slavedb.entity.ElementGroupStatistic;
import ru.on8off.postgres.repository.slavedb.entity.EntityStub;

import java.util.List;

@Repository
public interface ElementGroupStatisticRepository extends org.springframework.data.repository.Repository<EntityStub, Integer> {
    @Query(
            nativeQuery = true,
            value = "" +
            "select " +
            "   gr.\"name\" as groupName, " +
            "   count(e.id) as elementsCount " +
            "from element_groups gr " +
            "left join elements e on gr.id = e.element_group " +
            "group by 1;"
    )
    List<ElementGroupStatistic> getGroupStatistic();
}
