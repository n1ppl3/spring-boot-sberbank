package com.n1ppl3.spring.scanning;

import com.n1ppl3.spring.scanning.main.MainConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@Slf4j
public class SpringScanningTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
        context.register(MainConfiguration.class);
        context.refresh();
        Arrays.asList(context.getBeanDefinitionNames()).forEach(name -> {
            BeanDefinition beanDefinition = context.getBeanDefinition(name);
            Class<?> type = context.getType(name);
            if (type.getCanonicalName().contains("springframework")) return;
            Object bean = context.getBean(name);
            logger.info("beanDefinition: {}\nname: {}\ntype: {}|bean: {}", beanDefinition, name, type, bean);
        });
    }

    static class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
            System.err.println("REGISTERED DEFINITIONS:");
            Arrays.asList(defaultListableBeanFactory.getBeanDefinitionNames()).forEach(name -> {
                Class<?> type = beanFactory.getType(name);
                if (type.getCanonicalName().contains("springframework")) return;
                System.err.println(name);
            });
        }
    }
}
