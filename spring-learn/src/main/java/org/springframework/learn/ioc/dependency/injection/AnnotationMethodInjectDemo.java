package org.springframework.learn.ioc.dependency.injection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.learn.ioc.domain.User;

import javax.annotation.Resource;

public class AnnotationMethodInjectDemo {

	private UserHolder userHolder1;

	private UserHolder userHolder2;

	@Autowired
	private void init1(UserHolder userHolder) {
		this.userHolder1 = userHolder;
	}

	@Resource
	private void init2(UserHolder userHolder2) {
		this.userHolder2 = userHolder2;
	}

	@Bean
	private UserHolder userHolder(User user) {
		return new UserHolder(user);
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AnnotationMethodInjectDemo.class);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		ctx.refresh();
		AnnotationMethodInjectDemo demo = ctx.getBean(AnnotationMethodInjectDemo.class);
		System.out.println(demo.userHolder1);
		System.out.println(demo.userHolder2);
		ctx.close();
	}
}
