package ru.on8off.postgres.repository.masterdb;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.on8off.postgres.repository.masterdb.entity.Counter;
import ru.on8off.postgres.repository.masterdb.entity.Element;

@Repository
public interface CounterRepository extends CrudRepository<Counter, Integer>, JpaSpecificationExecutor<Element> {
    Counter findCounterByName(String name);

    @Query(value = "select * from counters where name=:name for update", nativeQuery = true)
    Counter selectCounterForUpdate(String name);

    @Modifying(clearAutomatically = true)
    @Query(value = "update counters set value=value+1 where name=:name", nativeQuery = true)
    void incrementCounter(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
           value =
            "insert into counters(name, value, date_created, date_last_modified) " +
            "values (:name, 1, now(), now()) " +
            "on conflict(name) do update set value=counters.value+1")
    void createOrUpdateCounter(@Param("name") String name);
}
