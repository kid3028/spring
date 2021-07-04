package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.learn.ioc.domain.User;

public class XmlSetterInjectDemo {

	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		String location = "classpath:/META-INF/setter-inject-context.xml";
		// 加载xml资源，解析并生成BeanDefinition
		reader.loadBeanDefinitions(location);
		// 依赖查找并创建bean
		UserHolder userHolder = beanFactory.getBean(UserHolder.class);
		System.out.println(userHolder);

	}


}
