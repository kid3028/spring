package org.springframework.learn.ioc.metadata;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.learn.ioc.domain.User;

public class ExtensibleXmlAuthoringDemo {
	public static void main(String[] args) {
		try {

			DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
			reader.loadBeanDefinitions("classpath:/META-INF/user-context.xml");
			System.out.println(beanFactory.getBean(User.class));

		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
