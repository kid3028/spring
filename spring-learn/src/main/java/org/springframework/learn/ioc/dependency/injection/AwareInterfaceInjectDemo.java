package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AwareInterfaceInjectDemo implements BeanFactoryAware, ApplicationContextAware {

	private static BeanFactory beanFactory;

	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AwareInterfaceInjectDemo.class);
		ctx.refresh();

		// true
		System.out.println(beanFactory == ctx.getBeanFactory());
		// true
		System.out.println(applicationContext == ctx);

		ctx.close();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		AwareInterfaceInjectDemo.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AwareInterfaceInjectDemo.applicationContext = applicationContext;
	}
}
