package org.springframework.learn.bean.definition;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.learn.ioc.domain.User;

public class BeanDefinitionCreationDemo {

	public static void main(String[] args) {
		// 1、通过BeanDefinitionBuilder
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
		// 通过属性设置
		beanDefinitionBuilder.addPropertyValue("id", 11).addPropertyValue("name", "spring bean");

		// 获取BeanDefinition实例
		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
		// BeanDefinition 并非 bean 最终形态，可自定义修改
//		beanDefinition.setAbstract(false);

		// 2、通过 AbstractBeanDefinition 以及 派生类
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(User.class);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
//		propertyValues.addPropertyValue("id", 11);
//		propertyValues.addPropertyValue("name", "spring bean");

		propertyValues.add("id", 11).add("name", "spring bean");

		genericBeanDefinition.setPropertyValues(propertyValues);
	}
}
