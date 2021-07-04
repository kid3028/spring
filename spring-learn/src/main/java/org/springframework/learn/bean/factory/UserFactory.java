package org.springframework.learn.bean.factory;

import org.springframework.learn.ioc.domain.User;

/**
 * interface 改为 class 便是静态工厂
 * 这离由于示例抽象工厂，DefaultUserFactory将会继承该类，所以使用默认实现
 */
public interface UserFactory {
	default User createUser() {
		return User.createUser();
	}
}
