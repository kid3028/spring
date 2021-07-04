package org.springframework.learn.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.learn.ioc.domain.User;

/**
 * Bean元信息配置示例
 */
public class BeanMetadataConfigurationDemo {
	public static void main(String[] args) {
		try {

			DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
			PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
			// properties默认使用iso8859-1读取，中文会出现乱码
			reader.loadBeanDefinitions(new EncodedResource(new ClassPathResource("META-INF/user.properties"), "UTF-8"));
			System.out.println(beanFactory.getBean(User.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
