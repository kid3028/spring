package org.springframework.learn.bean.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.learn.ioc.domain.User;

public class UserFactoryBean implements FactoryBean<User> {
	@Override
	public User getObject() throws Exception {
		return User.createUser();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
