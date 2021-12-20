package ru.on8off.postgres;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.postgres.repository.masterdb.ElementGroupRepository;
import ru.on8off.postgres.repository.masterdb.ElementRepository;
import ru.on8off.postgres.repository.masterdb.entity.Element;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroup;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroupType;
import ru.on8off.postgres.repository.slavedb.entity.ElementGroupStatistic;
import ru.on8off.postgres.service.StatisticService;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class StatisticServiceIT {
    @Autowired
    StatisticService statisticService;
    @Autowired
    ElementRepository elementRepository;
    @Autowired
    ElementGroupRepository elementGroupRepository;

    @Test
    void getGroupStatistics() {
        var groupName1 = "Test Group 1 - " + UUID.randomUUID();
        var groupName2 = "Test Group 2 - " + UUID.randomUUID();
        var groupName3 = "Test Group 3 - " + UUID.randomUUID();
        setUp(groupName1, groupName2, groupName3);

        var list = statisticService.getGroupStatistics();
        assertEquals(1, getCount(list,groupName1));
        assertEquals(2, getCount(list,groupName2));
        assertEquals(0, getCount(list,groupName3));

    }

    private void setUp(String groupName1, String groupName2, String groupName3){
        var group1 = new ElementGroup();
        group1.setName(groupName1);
        group1.setType(ElementGroupType.TYPE1);
        group1 = elementGroupRepository.save(group1);

        var group2 = new ElementGroup();
        group2.setName(groupName2);
        group2.setType(ElementGroupType.TYPE1);
        group2 = elementGroupRepository.save(group2);

        var group3 = new ElementGroup();
        group3.setName(groupName3);
        group3.setType(ElementGroupType.TYPE1);
        group3 = elementGroupRepository.save(group3);

        var element1 = new Element();
        element1.setName(UUID.randomUUID().toString());
        element1.setElementGroup(group1);
        elementRepository.save(element1);

        var element2 = new Element();
        element2.setName(UUID.randomUUID().toString());
        element2.setElementGroup(group2);
        elementRepository.save(element2);

        var element3 = new Element();
        element3.setName(UUID.randomUUID().toString());
        element3.setElementGroup(group2);
        elementRepository.save(element3);
    }

    private Integer getCount(List<ElementGroupStatistic> list, String group){
        return list.stream().filter(s-> s.getGroupName().equals(group)).findFirst()
                            .map(ElementGroupStatistic::getElementsCount)
                            .orElse(null);
    }
}