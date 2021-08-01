package org.springframework.learn.annotation.component;

import org.springframework.stereotype.Component;

@Component
public class User {

	private String remark = "AnnotationBeanDefinition";

	@Override
	public String toString() {
		return "User{" +
				"remark='" + remark + '\'' +
				'}';
	}
}
