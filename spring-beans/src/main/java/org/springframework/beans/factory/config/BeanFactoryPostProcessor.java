/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * BeanFactoryPostProcessor对beanDefinition进行处理。可以配置多个BeanFactoryPostProcessor
 * ioc容器允许BeanFactoryPostProcessor在容器实际实例化任何其他bean之前读取配置袁术，并有可能修改它。
 * 可以通过设置 order 属性来控制BeanFactoryPostProcessor的执行次序（当且仅当BeanFactoryPostProcessor实现了Ordered
 * 接口是才可以设置该属性，因此在实现BeanFactoryPostProcessor时，应考虑实现Ordered接口）
 *
 * 如果想改变实际的Bean实例(例如像从配置元数据创建的对象)，那么最好使用BeanPostProcessor
 * BeanFactoryPostProcessor的作用域是容器级的，
 *
 * BeanFactoryPostProcessor 的典型使用 —— PropertyPlaceholderConfigurer
 *   <bean id="message" class="com.test.Message">
 *   	<property name="msg">
 *   		<value>${bean.message}</value>
 *   	</property>
 *   </bean>
 * 	 ${bean.message} 就是spring的分散配置，可以在另外的配置文件中位bean.message指定值，如
 * 	   bean.message=hi,i am message!
 * 当访问message这个bean时，msg属性就会被置为“hi,i am message!”
 * PropertyPlaceholderConfigurer的作用就是告诉spring该文件
 *    <bean id="msgHandler" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
 *    	<property name="locations">
 *    		<value>config/bean.properties</value>
 *    	</property>
 *    </bean>
 * Allows for custom modification of an application context's bean definitions,
 * adapting the bean property values of the context's underlying bean factory.
 *
 * <p>Application contexts can auto-detect BeanFactoryPostProcessor beans in
 * their bean definitions and apply them before any other beans get created.
 *
 * <p>Useful for custom config files targeted at system administrators that
 * override bean properties configured in the application context.
 *
 * <p>See PropertyResourceConfigurer and its concrete implementations
 * for out-of-the-box solutions that address such configuration needs.
 *
 * <p>A BeanFactoryPostProcessor may interact with and modify bean
 * definitions, but never bean instances. Doing so may cause premature bean
 * instantiation, violating the container and causing unintended side-effects.
 * If bean instance interaction is required, consider implementing
 * {@link BeanPostProcessor} instead.
 *
 * @author Juergen Hoeller
 * @since 06.07.2003
 * @see BeanPostProcessor
 * @see PropertyResourceConfigurer
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {

	/**
	 * Modify the application context's internal bean factory after its standard
	 * initialization. All bean definitions will have been loaded, but no beans
	 * will have been instantiated yet. This allows for overriding or adding
	 * properties even to eager-initializing beans.
	 * @param beanFactory the bean factory used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
