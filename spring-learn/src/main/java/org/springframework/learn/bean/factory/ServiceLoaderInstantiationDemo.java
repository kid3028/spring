package org.springframework.learn.bean.factory;

import java.util.ServiceLoader;

/**
 * ServiceLoader使用
 *   1、在META-INF/service 下以接口全限定名定义文件
 *   2、文件内填写接口实现
 *   3、代码load接口，即可得到相关示例
 * @see java.util.ServiceLoader
 * @see org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean
 */
public class ServiceLoaderInstantiationDemo {

	public static void main(String[] args) {
		ServiceLoader<UserFactory> userFactories = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
		for (UserFactory userFactory : userFactories) {
			System.out.println(userFactory.createUser());
		}

	}
}
