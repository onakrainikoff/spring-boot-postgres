package ru.on8off.postgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.postgres.repository.masterdb.ElementRepository;
import ru.on8off.postgres.repository.masterdb.entity.Element;

import java.util.List;

@Service
public class ElementsService {
    @Autowired
    private ElementRepository elementRepository;

    @Transactional(readOnly = true)
    public Element get(Integer id){
        return elementRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Element> get(String name){
        return elementRepository.findElementsByName(name);
    }

    @Transactional
    public Element save(Element element){
        return elementRepository.save(element);
    }

    @Transactional
    public void delete(Integer id){
        elementRepository.deleteById(id);
    }
}
