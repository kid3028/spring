package org.springframework.learn.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@EnableHelloWorld
public class EnableModuleDemo {

	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(EnableModuleDemo.class);
			ctx.refresh();
			System.out.println(ctx.getBean("helloWorld", String.class));
			ctx.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
