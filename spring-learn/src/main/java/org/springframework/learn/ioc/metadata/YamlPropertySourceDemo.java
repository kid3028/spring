package org.springframework.learn.ioc.metadata;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class YamlPropertySourceDemo {
	public static void main(String[] args) {
		try {
			DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
			reader.loadBeanDefinitions("classpath:/META-INF/yaml-context.xml");
			System.out.println(beanFactory.getBean("yamlMap", Map.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
