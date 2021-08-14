package org.springframework.learn.ioc.container;

import org.springframework.stereotype.Component;

@Component
public class User {
	private String id = "11";

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				'}';
	}
}
