package org.springframework.learn.resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

public class InjectResourceLoaderDemo implements ResourceLoaderAware, ApplicationContextAware {

	private ResourceLoader resourceLoader;

	private ApplicationContext applicationContext;

	@Autowired
	private ResourceLoader injectResourceLoader;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(InjectResourceLoaderDemo.class);
		ctx.refresh();
		ctx.close();
	}


	@PostConstruct
	public void init() {
		/*
		org.springframework.context.support.AbstractApplicationContext.prepareBeanFactory
		aware注入的都是同一个对象ApplicationContext
		 */
		System.out.println("resourceLoader == injectResourceLoader ? " + (resourceLoader == injectResourceLoader));
		System.out.println("resourceLoader == applicationContext ? " + (resourceLoader == applicationContext));

	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
