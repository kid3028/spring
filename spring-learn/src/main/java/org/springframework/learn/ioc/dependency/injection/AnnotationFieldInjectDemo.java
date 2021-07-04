package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.learn.ioc.domain.User;

import javax.annotation.Resource;

public class AnnotationFieldInjectDemo {

	@Autowired
	private UserHolder userHolder;

	@Resource
	private UserHolder userHolder2;

//	@Autowired   会忽略静态字段
//	private static UserHolder userHolder;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AnnotationFieldInjectDemo.class);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		ctx.refresh();

		AnnotationFieldInjectDemo demo = ctx.getBean(AnnotationFieldInjectDemo.class);
		System.out.println(demo.userHolder);
		System.out.println(demo.userHolder2);

		ctx.close();
	}

	public AnnotationFieldInjectDemo() {
		System.out.println("invoke AnnotationFieldInjectDemo constructor");
	}

	@Bean
	public UserHolder userHolder(User user) {
		System.out.println("invoke AnnotationFieldInjectDemo#userHolder");
		return new UserHolder(user);
	}
}
