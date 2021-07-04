package org.springframework.learn.bean.lifecycle;


import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.learn.ioc.domain.User;

/**
 * BeanDefinition合并示例
 */
public class MergedBeanDefinitionDemo {

	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(new EncodedResource(new ClassPathResource("META-INF/dependency-lookup-context.xml")));
		System.out.println(beanFactory.getBean("user", User.class));
		System.out.println(beanFactory.getBean("superUser", User.class));
	}
}
