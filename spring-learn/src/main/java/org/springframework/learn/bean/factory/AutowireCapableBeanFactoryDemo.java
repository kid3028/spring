package org.springframework.learn.bean.factory;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutowireCapableBeanFactoryDemo {
	public static void main(String[] args) {
		// 和xml无关，只是为了校验通过
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/service-loader-bean-instantiation-context.xml");
		AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
		DefaultUserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
		System.out.println(userFactory.createUser());
	}
}
