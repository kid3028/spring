package org.springframework.learn.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.learn.bean.factory.DefaultUserFactory;
import org.springframework.learn.bean.factory.UserFactory;

@Configuration  // Configuration Class
public class BeanInitializationDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(BeanInitializationDemo.class);
		ctx.refresh();
		// 在非延迟初始化的情况下，这里在bean初始化信息打印后打印
		// 延迟初始化情况下，这里在bean初始化之前打印
		System.out.println("spring应用上下文启动完成...");
		UserFactory userFactory = ctx.getBean(UserFactory.class);
		ctx.close();
	}

	// @PostConstruct
//	@Bean
//	public UserFactory userFactory() {
//		return new DefaultUserFactory();
//	}

	@Bean(initMethod = "beanAnnotationInitMethod")
	@Lazy
	public UserFactory userFactory() {
		return new DefaultUserFactory();
	}
}
