package ru.on8off.postgres;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.postgres.repository.masterdb.CounterRepository;
import ru.on8off.postgres.service.CounterService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CounterServiceIT {
    @Autowired
    CounterService counterService;
    @Autowired
    CounterRepository counterRepository;

    @Test
    void testIncrement() throws InterruptedException, ExecutionException {
        var counterName = "test - " + UUID.randomUUID();
        var executor = Executors.newFixedThreadPool(10);
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            var n = i;
            list.add(executor.submit(()-> {
                counterService.increment(counterName);
            }));
            if(i % 10 == 0) {
                Thread.sleep(10);
            }
        }
        for (Future future : list) {
            future.get();
        }
        executor.awaitTermination(1, TimeUnit.SECONDS);
        var counter = counterRepository.findCounterByName(counterName);
        assertEquals(1000, counter.getValue());
    }
}