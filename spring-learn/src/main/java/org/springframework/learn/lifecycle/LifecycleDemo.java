package org.springframework.learn.lifecycle;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.GenericApplicationContext;

public class LifecycleDemo {
	public static void main(String[] args) {
		GenericApplicationContext ctx = new GenericApplicationContext();
		ctx.registerBeanDefinition("myLifecycle", BeanDefinitionBuilder.rootBeanDefinition(MyLifecycle.class).getBeanDefinition());
		ctx.refresh();
		ctx.start();
		ctx.stop();
		ctx.close();
	}
}
