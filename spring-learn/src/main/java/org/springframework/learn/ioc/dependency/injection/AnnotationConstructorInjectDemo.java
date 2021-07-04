package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.learn.ioc.domain.User;

public class AnnotationConstructorInjectDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		ctx.refresh();

		System.out.println(ctx.getBean(User.class));

		ctx.close();


	}


	@Bean
	public UserHolder userHolder(User user) {
		return new UserHolder(user);
	}
}


