package org.springframework.learn.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * 注解BeanDefinition解析
 */
public class AnnotatedBeanDefinitionParsingDemo {

	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
		// 注册的bean不一定是 @Component
		reader.register(AnnotatedBeanDefinitionParsingDemo.class);
		System.out.println(beanFactory.getBean(AnnotatedBeanDefinitionParsingDemo.class));
	}


}
