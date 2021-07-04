package org.springframework.learn.ioc.java.bean;

import java.beans.*;
import java.util.stream.Stream;

public class BeanInfoDemo {

	public static void main(String[] args) throws IntrospectionException {
		// bean 自省
		// 配置原信息存储对象
		BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
		// 列出属性，由于Object.getClass()方法干扰，会解析为Person类中有一个叫class的属性
		// 故在getBeanInfo中添加第二个参数屏蔽 Object
		Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor -> {
			// propertyDescriptor 允许添加属性编辑器  propertyEditor
//			propertyDescriptor.setPropertyEditorClass();
			Class<? extends PropertyDescriptor> propertyType = propertyDescriptor.getClass();
			if ("age".equals(propertyDescriptor.getName())) {
				propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
			}
		});
	}

	static class StringToIntegerPropertyEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			Integer val = Integer.valueOf(text);
			setValue(val);
		}
	}

}
