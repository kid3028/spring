package org.springframework.learn.bean.factory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;

public class ServiceLoaderFactoryBeanInstantiationDemo {

	@SuppressWarnings({"unchecked"})
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/service-loader-bean-instantiation-context.xml");
		ServiceLoader<UserFactory> serviceLoader = ctx.getBean("user-created-by-service-loader", ServiceLoader.class);
		for (UserFactory userFactory : serviceLoader) {
			System.out.println(userFactory.createUser());
		}
	}
}
