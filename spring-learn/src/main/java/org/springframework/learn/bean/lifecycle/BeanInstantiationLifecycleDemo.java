package org.springframework.learn.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.dependency.injection.UserHolder;
import org.springframework.learn.ioc.domain.SuperUser;
import org.springframework.learn.ioc.domain.User;
import org.springframework.util.ObjectUtils;

public class BeanInstantiationLifecycleDemo {

	public static void main(String[] args) {
		try {
//			DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//			beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
//			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader();
//			reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml", "classpath:/META-INF/constructor-instantiation-bean-lifecycle.xml");
			ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml", "classpath:/META-INF/constructor-instantiation-bean-lifecycle.xml");
			beanFactory.refresh();

			System.out.println(beanFactory.getBean("superUser", SuperUser.class));
			System.out.println(beanFactory.getBean("user", User.class));
			System.out.println(beanFactory.getBean(UserHolder.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
		@Override
		public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
			if (ObjectUtils.nullSafeEquals("superUser", beanName)) {
				// 覆盖xml配置的superUser
				return new SuperUser();
			}
			// 保持Spring IoC容器实例化xml配置的superUser
			return null;
		}

		/**
		 * false终止属性赋值
		 * true执行属性赋值
		 * @param bean the bean instance created, with properties not having been set yet
		 * @param beanName the name of the bean
		 * @return
		 * @throws BeansException
		 */
		@Override
		public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
			if (ObjectUtils.nullSafeEquals("user", beanName)) {
				User user = (User)bean;
				user.setName("赋值终止");
				return false;
			}
			return true;
		}

		@Override
		public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
			if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
				if (pvs instanceof MutablePropertyValues) {
					MutablePropertyValues p = (MutablePropertyValues) pvs;
					if (p.contains("number")) {
						p.add("number", 2);
					}
				}
				return pvs;
			}
			return null;
		}
	}

}
