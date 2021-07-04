package org.springframework.learn.bean.definition;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.domain.User;

public class BeanAliasDemo {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/bean-definition-alias-context.xml");
		User aliasUser = ctx.getBean("alias-user", User.class);
		User user = ctx.getBean("user", User.class);
		System.out.println("alias user : " + aliasUser);
		System.out.println("aliasUser.equals(user) ? " + aliasUser.equals(user));
	}
}
