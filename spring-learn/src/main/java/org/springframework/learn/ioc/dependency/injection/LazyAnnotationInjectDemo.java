package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.learn.ioc.domain.User;

import java.util.List;

public class LazyAnnotationInjectDemo {

	/**
	 * 实时注入
	 */
	@Autowired
	private User user;

	/**
	 * 延迟注入
	 */
	@Autowired
	private ObjectProvider<List<User>> objectProvider;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		ctx.register(LazyAnnotationInjectDemo.class);
		ctx.refresh();

		LazyAnnotationInjectDemo demo = ctx.getBean(LazyAnnotationInjectDemo.class);
		System.out.println(demo.user);
		// 继承了ObjectFactory#getObject
		System.out.println(demo.objectProvider.getObject());

		ctx.close();
	}
}
