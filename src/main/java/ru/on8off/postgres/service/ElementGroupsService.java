package ru.on8off.postgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.postgres.repository.masterdb.ElementGroupRepository;
import ru.on8off.postgres.repository.masterdb.entity.Element;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroup;
import ru.on8off.postgres.repository.masterdb.entity.ElementGroupType;

import java.util.List;

@Service
public class ElementGroupsService {
    @Autowired
    private ElementGroupRepository elementGroupRepository;

    @Transactional(readOnly = true)
    public ElementGroup get(Integer id){
        return elementGroupRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public ElementGroup get(String name){
        return elementGroupRepository.findElementGroupByName(name);
    }

    @Transactional(readOnly = true)
    public List<ElementGroup> get(ElementGroupType type){
        return elementGroupRepository.findElementGroupsByType(type);
    }

    @Transactional
    public ElementGroup save(ElementGroup elementGroup){
        return elementGroupRepository.save(elementGroup);
    }

    @Transactional
    public void delete(Integer id){
        elementGroupRepository.deleteById(id);
    }
}
