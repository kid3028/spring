package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiSetterInjectDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.registerBeanDefinition("userHolder", createUserHolderBeanDefinition());
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		ctx.refresh();
		System.out.println(ctx.getBean(UserHolder.class));
		ctx.close();
	}

	public static BeanDefinition createUserHolderBeanDefinition() {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
		builder.addPropertyReference("user", "superUser");
		return builder.getBeanDefinition();
	}
}
