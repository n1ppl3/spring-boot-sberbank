package com.n1ppl3.spring.scanning.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.lang.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@ComponentScan(
    basePackages = {"com.n1ppl3.spring.scanning.main"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MainConfiguration.StartsWithComponentScanFilter.class}),
    }
)
public class MainConfiguration {

    @Bean
    public Integer mainConfigurationIntegerBean() {
        return 100500;
    }

    public static class StartsWithComponentScanFilter extends AbstractClassTestingTypeFilter {

        private static final String STARTS_WITH = "com.n1ppl3.spring.scanning.main.sub";

        private static final AtomicInteger counter = new AtomicInteger();

        @Override
        protected boolean match(@NonNull ClassMetadata metadata) {
            String className = metadata.getClassName();
            boolean result = className.startsWith(STARTS_WITH);
            System.err.println(counter.incrementAndGet() + "\t" + result + "\t for '" + className + "'");
            return result;
        }
    }

}
