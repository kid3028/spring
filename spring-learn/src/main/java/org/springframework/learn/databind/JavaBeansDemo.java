package org.springframework.learn.databind;

import org.springframework.learn.ioc.domain.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;

public class JavaBeansDemo {

	public static void main(String[] args) throws IntrospectionException {
//		BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
		BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);

		// 所有类都继承自Object，class属性来自于Object.getClass()方法
		Arrays.stream(beanInfo.getPropertyDescriptors())
				.forEach(propertyDescriptor -> {
//					propertyDescriptor.getPropertyType();
//					propertyDescriptor.getReadMethod();
//					propertyDescriptor.getWriteMethod();
					System.out.println(propertyDescriptor);
				});

		Arrays.stream(beanInfo.getMethodDescriptors())
				.forEach(methodDescriptor -> {
					System.out.println(methodDescriptor);
				});

	}
}
