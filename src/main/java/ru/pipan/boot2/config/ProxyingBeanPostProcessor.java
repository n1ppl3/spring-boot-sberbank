package ru.pipan.boot2.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class ProxyingBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.contains("weatherController")) {
			Object result = proxyFactoryBeanForClass(bean.getClass(), new MyMethodInterceptor(bean));
			logger.warn("AfterInitialization() : {}: {}/{}", beanName, result, result.getClass().getCanonicalName());
			return result;
		}
		return bean;
	}

	@SuppressWarnings({"unchecked"})
	private static <T> T proxyFactoryBeanForClass(Class<T> targetClass, MethodInterceptor methodInterceptor) {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		if (targetClass.isInterface()) {
			proxyFactoryBean.setInterfaces(targetClass);
		} else {
			proxyFactoryBean.setTargetClass(targetClass);
		}
		proxyFactoryBean.addAdvice(methodInterceptor);
		return (T) proxyFactoryBean.getObject();
	}

	@Slf4j
	@AllArgsConstructor
	private static class MyMethodInterceptor implements MethodInterceptor {

		private final Object originalBean;

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			logger.info("Caught invocation: {}", invocation);
			Method method = invocation.getMethod();
			return method.invoke(originalBean, invocation.getArguments());
		}
	}
}
