package org.springframework.learn.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.learn.annotation.component.User;

@ComponentScan(basePackages = "org.springframework.learn.annotation.component")
public class AnnotationConfigApplicationContextTest {

	@Autowired
	private User user;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AnnotationConfigApplicationContextTest.class);
//		ctx.scan("org.springframework.learn.annotation.component");
		ctx.refresh();

//		System.out.println(ctx.getBean(User.class));
		System.out.println(ctx.getBean(AnnotationConfigApplicationContextTest.class).user);

		ctx.close();

	}

}
