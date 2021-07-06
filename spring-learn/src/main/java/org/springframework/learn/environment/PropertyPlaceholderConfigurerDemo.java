package org.springframework.learn.environment;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.domain.User;

public class PropertyPlaceholderConfigurerDemo {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/property-placeholder-resolver-context.xml");
		System.out.println(ctx.getBean(User.class));

	}
}
