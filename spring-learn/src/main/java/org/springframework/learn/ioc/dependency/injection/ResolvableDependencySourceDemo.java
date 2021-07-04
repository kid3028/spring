package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

public class ResolvableDependencySourceDemo {
	@Autowired
	private String msg;

	@Autowired
	private BeanFactory beanFactory;

	@PostConstruct
	public void init() {
		System.out.println("msg : " + msg);
		try {
			System.out.println(beanFactory.getBean(String.class));
		}catch (Exception e) {
			System.out.println("String.class 以ResolvableDependency形式注册到IoC容器，只能进行注入，不能查找. " + e.getMessage());
		}

	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ResolvableDependencySourceDemo.class);
		// exception 注入操作发生在register之前
//		ctx.getBeanFactory().registerResolvableDependency(String.class, "HelloWorld");

		// postProcessor在注入之前执行，故ok
		// org.springframework.context.support.AbstractApplicationContext.refresh 中
		//   org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors
		// 早于
		// org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization
		ctx.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerResolvableDependency(String.class, "HelloWorld"));
		ctx.refresh();

	}
}
