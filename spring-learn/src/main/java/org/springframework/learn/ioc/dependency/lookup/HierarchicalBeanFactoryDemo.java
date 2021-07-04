package org.springframework.learn.ioc.dependency.lookup;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HierarchicalBeanFactoryDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(HierarchicalBeanFactoryDemo.class);
		ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
		System.out.println("parent beanFactory : " + beanFactory.getParentBeanFactory());
		ConfigurableListableBeanFactory parentBeanFactory = createParentBeanFactory();
		beanFactory.setParentBeanFactory(parentBeanFactory);
		beanFactory = ctx.getBeanFactory();
		System.out.println("parent beanFactory : " + beanFactory.getParentBeanFactory());
		ctx.refresh();

		displayLocalBean(beanFactory, "user");
		displayLocalBean(parentBeanFactory, "user");

		ctx.close();

	}

	public static void displayLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
		System.out.printf("当前BeanFactory[%s]是否包含bean[name:%s] : %s \n", beanFactory, beanName, beanFactory.containsLocalBean(beanName));
	}


	private static ConfigurableListableBeanFactory createParentBeanFactory() {
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		return factory;
	}

}
