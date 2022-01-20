package org.springframework.learn.ioc.container;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class AutowireApplicationContextDemo {
	@Autowired
	private User user;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		// 将当前类注册为配置类，使@Bean注解能够被发现
		ctx.register(AutowireApplicationContextDemo.class);
		// 调用refresh启动IoC容器
		ctx.refresh();
		// 向IoC容器获取bean实例
		AutowireApplicationContextDemo demo = ctx.getBean(AutowireApplicationContextDemo.class);
		System.out.println(demo.user);
		// 关闭IoC容器
		ctx.close();
	}

	@Bean
	public User user() {
		return new User();
	}
}
