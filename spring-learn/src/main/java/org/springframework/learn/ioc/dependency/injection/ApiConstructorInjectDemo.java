package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiConstructorInjectDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
		builder.addConstructorArgReference("superUser");
		ctx.registerBeanDefinition("userHolder", builder.getBeanDefinition());
		ctx.refresh();

		System.out.println(ctx.getBean(UserHolder.class));
		ctx.close();

	}

}
