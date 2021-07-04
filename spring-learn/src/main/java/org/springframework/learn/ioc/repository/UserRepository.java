package org.springframework.learn.ioc.repository;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.learn.ioc.domain.User;

import java.util.Collection;

public class UserRepository {
	/**
	 * 自定义bean
	 */
	private Collection<User> users;

	/**
	 * 内建非 bean 对象，如果是内建bean对象，一定可以通过getBean获取到
	 * 属于内建依赖
 	 */
	private BeanFactory beanFactory;

	private ObjectFactory<User> userObjectFactory;

	private ObjectFactory<BeanFactory> beanFactoryObjectFactory;

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ObjectFactory<User> getUserObjectFactory() {
		return userObjectFactory;
	}

	public void setUserObjectFactory(ObjectFactory<User> userObjectFactory) {
		this.userObjectFactory = userObjectFactory;
	}

	public ObjectFactory<BeanFactory> getBeanFactoryObjectFactory() {
		return beanFactoryObjectFactory;
	}

	public void setBeanFactoryObjectFactory(ObjectFactory<BeanFactory> beanFactoryObjectFactory) {
		this.beanFactoryObjectFactory = beanFactoryObjectFactory;
	}
}
