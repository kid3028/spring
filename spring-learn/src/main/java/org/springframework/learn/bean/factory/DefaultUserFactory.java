package org.springframework.learn.bean.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

	@PostConstruct
	public void postConstruct() {
		System.out.println("@PostConstruct : userFactory 初始化中...");
	}

	public void beanAnnotationInitMethod() {
		System.out.println("@Bean(initMethod) : userFactory 初始化中...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean#afterPropertiesSet : userFactory 初始化中...");
	}


	@PreDestroy
	public void preDestroy() {
		System.out.println("@PreDestroy : UserFactory销毁中...");
	}

	public void beanAnnotationDestroy() {
		System.out.println("@Bean(destroy=\"destroy\") : UserFactory销毁中...");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean#destroy : UserFactory 销毁中...");
	}
}
