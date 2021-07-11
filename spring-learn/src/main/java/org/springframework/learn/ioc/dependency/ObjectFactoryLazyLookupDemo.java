package org.springframework.learn.ioc.dependency;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.learn.ioc.domain.User;

public class ObjectFactoryLazyLookupDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ObjectFactoryLazyLookupDemo.class);
		ctx.refresh();
		ObjectFactoryLazyLookupDemo bean = ctx.getBean(ObjectFactoryLazyLookupDemo.class);
		System.out.println("bean.objectFactory : " + bean.objectFactory);
		System.out.println("bean.objectProvider : " + bean.objectProvider);
		System.out.println("bean.objectFactory == bean.objectProvider ? " + (bean.objectFactory == bean.objectProvider));
		System.out.println("bean.objectFactory.getClass() == bean.objectProvider.getClass() ? " + (bean.objectFactory.getClass() == bean.objectProvider.getClass()));
		System.out.println("bean.objectFactory.getObject() : " + bean.objectFactory.getObject());
		System.out.println("bean.objectProvider.getObject() : " + bean.objectProvider.getObject());

	}


	@Autowired
	private ObjectFactory<User> objectFactory;

	@Autowired
	private ObjectProvider<User> objectProvider;

	@Bean
	@Lazy
	public static User user() {
		User user = new User();
		user.setId(11);
		user.setName("阿华哥");
		return user;
	}
}
