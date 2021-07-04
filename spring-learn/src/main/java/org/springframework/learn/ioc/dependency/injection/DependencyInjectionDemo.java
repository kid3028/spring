package org.springframework.learn.ioc.dependency.injection;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.learn.ioc.repository.UserRepository;

public class DependencyInjectionDemo {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		// 自定义bean
		System.out.println(userRepository.getUsers());

		// 内建依赖依赖注入
		System.out.println(userRepository.getBeanFactory());
		// 注入的beanFactory 和 创建的beanFactory不是一个
		System.out.println(userRepository.getBeanFactory() == ctx);

		// 无法获取到BeanFactory   依赖查找 (错误)
//		System.out.println(ctx.getBean(BeanFactory.class));

		System.out.println(userRepository.getUserObjectFactory().getObject());

		System.out.println(userRepository.getBeanFactoryObjectFactory().getObject());
		System.out.println(userRepository.getBeanFactoryObjectFactory().getObject() == ctx); // false
		System.out.println(userRepository.getBeanFactoryObjectFactory().getObject() == userRepository.getBeanFactory());  // true

		// 容器内建bean
		Environment env = ctx.getBean(Environment.class);
		System.out.println("获取 Environment 类型的bean : " + env);


	}
}
