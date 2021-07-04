package org.springframework.learn.ioc.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.annotation.Super;
import org.springframework.learn.ioc.domain.User;

import java.util.Map;

public class DependencyLookupDemo {

	public static void main(String[] args) {
		// 配置xml配置文件
		// 启动spring上下文
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
		lookupInRealTime(ctx);
		lookupInLazy(ctx);

		lookupByType(ctx);
		lookupCollectByType(ctx);

		lookupByAnnotation(ctx);

	}

	@SuppressWarnings({"unchecked"})
	private static void lookupByAnnotation(BeanFactory beanFactory) {
		if (beanFactory instanceof ListableBeanFactory) {
			ListableBeanFactory ctx = (ListableBeanFactory) beanFactory;
			Map<String, User> users = (Map) ctx.getBeansWithAnnotation(Super.class);
			System.out.println("按照annotation进行查找 : " + users);
		}
	}

	private static void lookupCollectByType(BeanFactory beanFactory) {
		if (beanFactory instanceof ListableBeanFactory) {
			ListableBeanFactory ctx = (ListableBeanFactory) beanFactory;
			Map<String, User> users = ctx.getBeansOfType(User.class);
			System.out.println("按照类型查找集合bean : " + users);
		}

	}

	private static void lookupByType(BeanFactory beanFactory) {
		User user = beanFactory.getBean(User.class);
		System.out.println("按照类型查找单个bean : " + user);
	}

	private static void lookupInRealTime(BeanFactory beanFactory) {
		User user = (User) beanFactory.getBean("user");
		System.out.println("实时查找 : " + user);
	}

	@SuppressWarnings({"unchecked"})
	private static void lookupInLazy(BeanFactory beanFactory) {
		ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
		User user = objectFactory.getObject();
		System.out.println("延迟查找 : " + user);
	}
}
