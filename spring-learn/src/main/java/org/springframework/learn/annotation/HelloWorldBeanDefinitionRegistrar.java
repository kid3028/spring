package org.springframework.learn.annotation;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class HelloWorldBeanDefinitionRegistrar  implements ImportBeanDefinitionRegistrar {
	@Override

	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotatedGenericBeanDefinition bd = new AnnotatedGenericBeanDefinition(HelloWorldConfiguration.class);
//		registry.registerBeanDefinition("helloWorldConfiguration", bd);
		BeanDefinitionReaderUtils.registerWithGeneratedName(bd, registry);
	}
}
