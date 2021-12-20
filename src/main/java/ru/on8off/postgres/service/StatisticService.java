package ru.on8off.postgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.on8off.postgres.repository.slavedb.ElementGroupStatisticRepository;
import ru.on8off.postgres.repository.slavedb.entity.ElementGroupStatistic;

import java.util.List;

@Service
public class StatisticService {
    @Autowired
    private ElementGroupStatisticRepository elementGroupStatisticRepository;

    @Transactional(transactionManager ="slaveTransactionManager" ,readOnly = true)
    public List<ElementGroupStatistic> getGroupStatistics(){
        return elementGroupStatisticRepository.getGroupStatistic();
    }
}
