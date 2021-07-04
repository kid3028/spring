package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.learn.ioc.domain.User;

public class UserHolder implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware, EnvironmentAware , SmartInitializingSingleton {
	private User user;

	private Integer number;

	private String desc;

	private ClassLoader classLoader;

	private BeanFactory beanFactory;

	private String beanName;

	private Environment environment;

	public UserHolder() {
	}

	public UserHolder(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public String getBeanName() {
		return beanName;
	}

	@Override
	public String toString() {
		return "UserHolder{" +
				"user=" + user +
				", number=" + number +
				", desc='" + desc + '\'' +
				", beanName='" + beanName + '\'' +
				'}';
	}


	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.setDesc("modified by SmartInitializingSingleton");
	}
}
