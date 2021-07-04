package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class AutowiringSetterInjectDemo {
	public static void main(String[] args) {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions("classpath:/META-INF/autowiring-setter-inject-context.xml");
		System.out.println(factory.getBean(UserHolder.class));
	}
}
