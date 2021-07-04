package org.springframework.learn.bean.metadata;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.learn.ioc.domain.User;
import org.springframework.util.ObjectUtils;

/**
 * bean配置元信息
 */
public class BeanConfigurationMetadataDemo {
	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// beanDefinition定义
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
		builder.addPropertyValue("name", "阿华哥");
		// 获取BeanDefinition
		AbstractBeanDefinition bd = builder.getBeanDefinition();
		// 附加属性（不影响Bean 赋值、初始化等）
		bd.setAttribute("name", "qull");
		// 标记bd的注册是来自哪里
		bd.setSource(BeanConfigurationMetadataDemo.class);
		beanFactory.registerBeanDefinition("user", bd);
		beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
					BeanDefinition bd = beanFactory.getBeanDefinition("user");
					User user = (User) bean;
					// 属性存储上下文，只是中间数据存储，不会作用于bean
					user.setName((String) bd.getAttribute("name"));

				}
				return bean;
			}
		});

		System.out.println(beanFactory.getBean(User.class));
	}
}
