package org.springframework.learn.ioc.java.bean;

/**
 * setter : writable
 * getter : readable
 * 属性 : property
 */
public class Person {

	String name;

	Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
