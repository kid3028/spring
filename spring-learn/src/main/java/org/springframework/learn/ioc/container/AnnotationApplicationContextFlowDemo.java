package org.springframework.learn.ioc.container;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationApplicationContextFlowDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AnnotationApplicationContextFlowDemo.class);
		ctx.refresh();
		System.out.println(ctx.getBean(User.class));
		ctx.close();
	}
}
