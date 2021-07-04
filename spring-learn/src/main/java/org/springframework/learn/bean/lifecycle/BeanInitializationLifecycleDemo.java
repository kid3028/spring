package org.springframework.learn.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.dependency.injection.UserHolder;
import org.springframework.learn.ioc.domain.SuperUser;
import org.springframework.learn.ioc.domain.User;
import org.springframework.util.ObjectUtils;

public class BeanInitializationLifecycleDemo {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml", "classpath:/META-INF/constructor-instantiation-bean-lifecycle.xml");
		// not work
//		beanFactory.getBeanFactory().addBeanPostProcessor(new MyBeanPostProcessor());
		// work
		beanFactory.addBeanFactoryPostProcessor(factory -> {
			factory.addBeanPostProcessor(new MyBeanPostProcessor());
		});
		beanFactory.refresh();

		System.out.println(beanFactory.getBean("superUser", SuperUser.class));
		System.out.println(beanFactory.getBean("user", User.class));
		System.out.println(beanFactory.getBean(UserHolder.class));
	}

	/**
	 * 如果返回null，那么后续的postProcessor就不会得到执行
	 */
	static class MyBeanPostProcessor implements BeanPostProcessor {
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
			if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
				UserHolder userHolder = (UserHolder) bean;
				userHolder.setDesc("modified by beanPostProcessor#postProcessBeforeInitialization");
			}
			return bean;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
			if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
				UserHolder userHolder = (UserHolder) bean;
				userHolder.setDesc("modified by beanPostProcessor#postProcessAfterInitialization");
			}
			return bean;		}
	}
}
