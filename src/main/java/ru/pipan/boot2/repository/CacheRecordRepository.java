package ru.pipan.boot2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pipan.boot2.entity.CacheRecord;

@Repository
public interface CacheRecordRepository extends CrudRepository<CacheRecord, Long> {

	CacheRecord findByCityName(String cityName);
}
