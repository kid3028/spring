package org.springframework.learn.ioc.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ObjectProviderDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ObjectProviderDemo.class);
		ctx.refresh();
		lookupByObjectProvider(ctx);
		ctx.close();
	}

	@Bean
	public String helloWorld() {
		return "Hello World";
	}

	public static void lookupByObjectProvider(AnnotationConfigApplicationContext ctx) {
		ObjectProvider<String> beanProvider = ctx.getBeanProvider(String.class);
		System.out.println(beanProvider.getObject());
	}
}
