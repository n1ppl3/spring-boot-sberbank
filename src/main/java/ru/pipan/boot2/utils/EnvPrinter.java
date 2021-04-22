package ru.pipan.boot2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class EnvPrinter {

    @Autowired
    public EnvPrinter(ConfigurableEnvironment environment) {
        getPropertySources(environment).forEach((sourceName, sourceProperties) -> {
            print(" - - - - - - - - - - - - - - - >>> " + sourceName.toUpperCase(Locale.US));
            sourceProperties.forEach((key, value) -> print(key + "=" + value));
        });
    }

    private static Map<String, Map<String, Object>> getPropertySources(ConfigurableEnvironment env) {
        Map<String, Map<String, Object>> result = new LinkedHashMap<>();

        env.getPropertySources().forEach(propertySource -> {
            if (propertySource instanceof EnumerablePropertySource) {
                Map<String, Object> properties = new LinkedHashMap<>();
                EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) propertySource;
                for (String name : enumerablePropertySource.getPropertyNames()) {
                    properties.put(name, propertySource.getProperty(name));
                }
                result.put(propertySource.getName(), properties);
            } else {
                print("Unknown property source " + propertySource);
            }
        });

        return result;
    }

    private static void print(String msg) {
        System.out.println("\t" + msg);
    }
}
