package ru.on8off.postgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.postgres.repository.masterdb.CounterRepository;

@Service
public class CounterService {
    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private CounterService counterService;

    @Transactional
    public void increment(String counterName){
        var counter = counterRepository.selectCounterForUpdate(counterName);
        if(counter == null){
            counterRepository.createOrUpdateCounter(counterName);
        } else {
            counterRepository.incrementCounter(counterName);
        }
    }
}
