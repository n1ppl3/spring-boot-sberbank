package ru.pipan.boot2.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.pipan.boot2.entity.CacheRecord;
import ru.pipan.boot2.repository.CacheRecordRepository;

@Configuration
@EntityScan(basePackageClasses = {CacheRecord.class})
@EnableJpaRepositories(basePackageClasses = {CacheRecordRepository.class})
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class DataJpaConfiguration {

}
