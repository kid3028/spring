package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiringConstructorInjectDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/autowiring-constructor-inject-context.xml");
		ctx.refresh();
		System.out.println(ctx.getBean(UserHolder.class));
		ctx.close();
	}
}
