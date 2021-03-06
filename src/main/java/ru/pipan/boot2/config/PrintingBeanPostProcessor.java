package ru.pipan.boot2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrintingBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (isOk(beanName)) logger.warn("BeforeInitialization(): {}: {}/{}", beanName, bean, bean.getClass().getCanonicalName());
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (isOk(beanName)) logger.warn("AfterInitialization() : {}: {}/{}", beanName, bean, bean.getClass().getCanonicalName());
		return bean;
	}

	private static boolean isOk(String beanName) {
		return beanName.contains("weather");
	}
}
