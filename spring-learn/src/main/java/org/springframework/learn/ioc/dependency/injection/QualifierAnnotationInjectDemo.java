package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.learn.ioc.annotation.UserGroup;
import org.springframework.learn.ioc.domain.User;

import java.util.List;

public class QualifierAnnotationInjectDemo {

	@Autowired
	private User user;

	@Autowired
	@Qualifier("user")
	private User namedUser;

	@Autowired
	private List<User> users;

	@Autowired
	@Qualifier
	private List<User> qualifiedUsers;

	@Autowired
	@Qualifier("group")
	private List<User> groupUsers;

	@Bean
	@Qualifier
	public User qualifiedUser1() {
		User user = new User();
		user.setId(11);
		return user;
	}

	@Bean
	@Qualifier
	public User qualifiedUser2() {
		User user = new User();
		user.setId(12);
		return user;
	}

	@Bean
	@Qualifier("group")
	public User groupUser() {
		User user = new User();
		user.setId(13);
		return user;
	}

	@Bean
	@Qualifier("group1")
	public User groupUser2() {
		User user = new User();
		user.setId(14);
		return user;
	}

	@Bean
	@UserGroup
	public User groupUser3() {
		User user = new User();
		user.setId(22);
		return user;

	}

	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

			ctx.register(QualifierAnnotationInjectDemo.class);

			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
			reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

			ctx.refresh();

			QualifierAnnotationInjectDemo demo = ctx.getBean(QualifierAnnotationInjectDemo.class);
			System.out.println("user : " + demo.user);
			System.out.println("namedUser : " + demo.namedUser);
			System.out.println("users : " +  demo.users);
			System.out.println("qualified users : " + demo.qualifiedUsers);
			System.out.println("group users : " + demo.groupUsers);
			System.out.println("@UserGroup : " + demo.groupUser3());

			ctx.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
