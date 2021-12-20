package ru.on8off.postgres;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroup;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroupType;
import ru.on8off.postgres.service.ElementGroupsService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElementGroupsServiceIT {
    @Autowired
    ElementGroupsService elementGroupsService;

    @Test
    void testCRUD() {
        var name = "Test Group - " + UUID.randomUUID();
        var resultGroup = elementGroupsService.get(name);
        assertNull(resultGroup);

        var type = ElementGroupType.TYPE1;
        var resultGroups = elementGroupsService.get(type);
        assertEquals(0, resultGroups.size());

        var group = new ElementGroup();
        group.setName(name);
        group.setType(type);

        resultGroup = elementGroupsService.save(group);
        assertNotNull(resultGroup);
        assertNotNull(resultGroup.getId());
        assertEquals(group.getDateCreated(), resultGroup.getDateCreated());
        assertEquals(group.getName(), resultGroup.getName());
        assertEquals(group.getType(), resultGroup.getType());

        group = elementGroupsService.get(resultGroup.getId());
        assertNotNull(group);

        resultGroup = elementGroupsService.get(name);
        assertEquals(group, resultGroup);

        resultGroups = elementGroupsService.get(type);
        assertEquals(1, resultGroups.size());
        assertTrue(resultGroups.contains(group));

        elementGroupsService.delete(group.getId());
        group = elementGroupsService.get(resultGroup.getId());
        assertNull(group);
    }
}