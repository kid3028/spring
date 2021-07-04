package org.springframework.learn.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 1、通过EnableXXX命名
 * 2、导入具体实现
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 方法一： 通过@Import 导入ConfigurationClass实现
//@Import(HelloWorldConfiguration.class)
// 方法二： 通过@Import 导入ImportSelector接口实现
//@Import(HelloWorldImportSelector.class )
// 方法三： 通过ImportBeanDefinitionRegistrar实现
@Import(HelloWorldBeanDefinitionRegistrar.class)
public @interface EnableHelloWorld {

}
