package org.springframework.learn.ioc.container;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.learn.ioc.domain.User;

import java.util.Map;

/**
 * 即使不在ApplicationContext环境下，也可以加载bean文件，但是没有了事件、资源管理等ApplicationContext的特性
 */
public class BeanFactoryAsIocContainerDemo {

	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		lookupCollectionByType(beanFactory);
	}

	private static void lookupCollectionByType(BeanFactory beanFactory) {
		if (beanFactory instanceof ListableBeanFactory) {
			ListableBeanFactory factory = (ListableBeanFactory) beanFactory;
			Map<String, User> users = factory.getBeansOfType(User.class);
			System.out.println(users);
		}
	}

}
