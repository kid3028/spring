package org.springframework.learn.ioc.domain;

import javax.annotation.PostConstruct;
import java.util.Properties;

public class User {
	private Integer id;

	private String name;

	private Properties context;

	private String contextAsText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Properties getContext() {
		return context;
	}

	public void setContext(Properties context) {
		this.context = context;
	}

	public String getContextAsText() {
		return contextAsText;
	}

	public void setContextAsText(String contextAsText) {
		this.contextAsText = contextAsText;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", context=" + context +
				", contextAsText='" + contextAsText + '\'' +
				'}';
	}

	public static User createUser() {
		User user = new User();
		user.setId(11);
		user.setName("create by static factory method");
		return user;
	}

	@PostConstruct
	public void init() {
		System.out.println("user init invoked");
	}

}
