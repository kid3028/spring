package org.springframework.learn.ioc.container;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.learn.ioc.domain.User;

import java.util.Map;

@Configuration
public class AnnotationApplicationContextAsIocContainerDemo {
	public static void main(String[] args) {
		// 创建beanFactory
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		// 将当前类AnnotationApplicationContextAsIoCContainerDemo作为配置类
		ctx.register(AnnotationApplicationContextAsIocContainerDemo.class);
		// 启动应用上下文
		ctx.refresh();
		// 依赖查找集合对象
		lookupCollectionByType(ctx);
		// 关闭上下文
		ctx.close();
	}


	@Bean
	public User user() {
		User user = new User();
		user.setId(11);
		user.setName("Spring ApplicationContext");
		return user;
	}

	public static void lookupCollectionByType(BeanFactory beanFactory) {
		if (beanFactory instanceof ListableBeanFactory) {
			ListableBeanFactory factory = (ListableBeanFactory) beanFactory;
			Map<String, User> users = factory.getBeansOfType(User.class);
			System.out.println(users);

		}
	}
}
