package ru.on8off.postgres;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.postgres.repository.masterdb.ElementGroupRepository;
import ru.on8off.postgres.repository.masterdb.ElementRepository;
import ru.on8off.postgres.repository.masterdb.entity.Element;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroup;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroupType;
import ru.on8off.postgres.service.FindElementsService;
import ru.on8off.postgres.service.domain.Filtering;
import ru.on8off.postgres.service.domain.Paging;
import ru.on8off.postgres.service.domain.Sorting;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FindElementsServiceIT {
    @Autowired
    FindElementsService findElementsService;
    @Autowired
    ElementGroupRepository elementGroupRepository;
    @Autowired
    ElementRepository elementRepository;

    String groupName1;
    String groupName2;
    String groupName3;
    String elementName1;
    String elementName2;
    String elementName3;
    List<Element> listElements;

    @Test
    void testFiltering() {
        setup();
        var group2Id = listElements.get(1).getElementGroup().getId();

        var list = findElementsService.findElements(null, null, null).toList();
        assertTrue(list.containsAll(listElements));

        var filtering = Filtering.builder()
                                 .name(elementName2)
                                 .build();
        list = findElementsService.findElements(filtering, null, null).toList();
        assertEquals(1, list.size());
        assertTrue(list.contains(listElements.get(1)));

        filtering = Filtering.builder()
                             .groupIds(List.of(group2Id))
                             .build();
        list = findElementsService.findElements(filtering, null, null).toList();
        assertEquals(2, list.size());
        assertTrue(list.contains(listElements.get(1)));
        assertTrue(list.contains(listElements.get(2)));

        filtering = Filtering.builder()
                             .groupTypes(List.of(ElementGroupType.TYPE2))
                             .build();
        list = findElementsService.findElements(filtering, null, null).toList();
        assertTrue(list.contains(listElements.get(1)));
        assertTrue(list.contains(listElements.get(2)));

        filtering = Filtering.builder()
                             .name("Test Element 2%")
                             .groupIds(List.of(group2Id))
                             .groupTypes(List.of(ElementGroupType.TYPE2))
                             .build();
        list = findElementsService.findElements(filtering, null, null).toList();
        assertEquals(1, list.size());
        assertEquals(elementName2, list.get(0).getName()    );
    }

    @Test
    void testSorting(){
        setup();
        var listIds = listElements.stream().map(e -> e.getElementGroup().getId()).distinct().collect(Collectors.toList());
        var filtering = Filtering.builder().groupIds(listIds).build();

        var list = findElementsService.findElements(filtering, null, null).toList();
        assertEquals(3, list.size());
        assertEquals(elementName1, list.get(0).getName());
        assertEquals(elementName2, list.get(1).getName());
        assertEquals(elementName3, list.get(2).getName());

        var sorting = new Sorting().add("name", true);
        list = findElementsService.findElements(filtering, null, sorting).toList();
        assertEquals(3, list.size());
        assertEquals(elementName1, list.get(0).getName());
        assertEquals(elementName2, list.get(1).getName());
        assertEquals(elementName3, list.get(2).getName());

        sorting = new Sorting().add("name", false);
        list = findElementsService.findElements(filtering, null, sorting).toList();
        assertEquals(3, list.size());
        assertEquals(elementName1, list.get(2).getName());
        assertEquals(elementName2, list.get(1).getName());
        assertEquals(elementName3, list.get(0).getName());


        sorting = new Sorting().add("elementGroup.name", true).add("name", true);
        list = findElementsService.findElements(filtering, null, sorting).toList();
        assertEquals(3, list.size());
        assertEquals(elementName1, list.get(0).getName());
        assertEquals(elementName2, list.get(1).getName());
        assertEquals(elementName3, list.get(2).getName());

        sorting = new Sorting().add("elementGroup.name", false).add("name", true);
        list = findElementsService.findElements(filtering, null, sorting).toList();
        assertEquals(3, list.size());
        assertEquals(elementName1, list.get(2).getName());
        assertEquals(elementName2, list.get(0).getName());
        assertEquals(elementName3, list.get(1).getName());
    }

    @Test
    void testPaging(){
        setup();
        var listIds = listElements.stream().map(e -> e.getElementGroup().getId()).distinct().collect(Collectors.toList());
        var filtering = Filtering.builder().groupIds(listIds).build();
        var sorting = new Sorting().add("name", true);

        var list = findElementsService.findElements(filtering, null, sorting).toList();
        assertEquals(3, list.size());

        var paging = new Paging(0,2);
        list = findElementsService.findElements(filtering, paging, sorting).toList();
        assertEquals(2, list.size());

        paging = new Paging(1,2);
        list = findElementsService.findElements(filtering, paging, sorting).toList();
        assertEquals(1, list.size());
    }

    private void setup(){
        groupName1 = "Test Group 1 - " + UUID.randomUUID();
        groupName2 = "Test Group 2 - " + UUID.randomUUID();
        groupName3 = "Test Group 3 - " + UUID.randomUUID();
        elementName1 = "Test Element 1 - " + UUID.randomUUID();
        elementName2 = "Test Element 2 - " + UUID.randomUUID();
        elementName3 = "Test Element 3 - " + UUID.randomUUID();

        var group1 = new ElementGroup();
        group1.setName(groupName1);
        group1.setType(ElementGroupType.TYPE1);
        group1 = elementGroupRepository.save(group1);

        var group2 = new ElementGroup();
        group2.setName(groupName2);
        group2.setType(ElementGroupType.TYPE2);
        group2 = elementGroupRepository.save(group2);

        var group3 = new ElementGroup();
        group3.setName(groupName3);
        group3.setType(ElementGroupType.TYPE1);
        elementGroupRepository.save(group3);

        var element1 = new Element();
        element1.setName(elementName1);
        element1.setElementGroup(group1);
        elementRepository.save(element1);

        var element2 = new Element();
        element2.setName(elementName2);
        element2.setElementGroup(group2);
        elementRepository.save(element2);

        var element3 = new Element();
        element3.setName(elementName3);
        element3.setElementGroup(group2);
        elementRepository.save(element3);

        listElements =  List.of(element1, element2, element3);
    }
}