package org.springframework.learn.bean.definition;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.domain.User;

public class BeanInstantiationDemo {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
		User user = ctx.getBean("user-created-by-static-factory-method", User.class);
		System.out.println("静态工厂方法创建Bean : " + user);

		User userCreatedByFactory = ctx.getBean("user-created-by-static-factory", User.class);
		System.out.println("抽象工厂创建Bean : " + userCreatedByFactory);

		User userCreateByFactoryBean = ctx.getBean("user-created-by-factory-bean", User.class);
		System.out.println("FactoryBean创建Bean : " + userCreateByFactoryBean);
	}
}
