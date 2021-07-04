package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.learn.ioc.domain.User;

public class AnnotationSetterInjectDemo {

	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(AnnotationSetterInjectDemo.class);
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
			reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
			ctx.refresh();
			System.out.println(ctx.getBean(UserHolder.class));

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Bean
	public UserHolder userHolder(User user) {
		UserHolder userHolder = new UserHolder();
		userHolder.setUser(user);
		return userHolder;
	}
}
