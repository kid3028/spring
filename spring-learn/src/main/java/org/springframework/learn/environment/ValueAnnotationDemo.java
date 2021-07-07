package org.springframework.learn.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ValueAnnotationDemo {

	@Value("${user.name}")
	private String username;
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ValueAnnotationDemo.class);
		ctx.refresh();
		System.out.println(ctx.getBean(ValueAnnotationDemo.class).username);
		ctx.close();
	}
}
