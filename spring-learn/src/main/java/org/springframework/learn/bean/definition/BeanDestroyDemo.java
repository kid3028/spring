package org.springframework.learn.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.learn.bean.factory.DefaultUserFactory;
import org.springframework.learn.bean.factory.UserFactory;

@Configuration
public class BeanDestroyDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(BeanDestroyDemo.class);
		ctx.refresh();
		System.out.println("spring应用上下文启动完成...");

		UserFactory userFactory = ctx.getBean(UserFactory.class);
		System.out.println(userFactory);
		System.out.println("spring上下文准备关闭...");
		ctx.close();
		System.out.println("spring应用上下文已关闭...");
	}

	@Bean(destroyMethod = "beanAnnotationDestroy")
	public UserFactory userFactory() {
		return new DefaultUserFactory();
	}
}

