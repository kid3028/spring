/*
 * Copyright 2002-2017 the original author or authors.
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

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.lang.Nullable;

/**
 * SpEL：spring expression language，能在运行时构建复杂表达式、存取对象图属性、对象方法调用等，与spring完美整合。
 * SpEL是单独的模块，只依赖core模块，不依赖其他模块，可以单独使用。
 * SpEL 使用 #{...} 作为定界符，所有在大括号中的字符被认为是SpEL
 *    <bean id="saxophone" value="com.test.spel"/>
 *    <bean>
 *        <property name="instrument" value="#{saxophone}"/>
 *    </bean>
 *
 *    等价于
 *
 *    <bean id="saxophone" value="com.test.spel"/>
 *    <bean>
 *        <property name="instrument" ref="saxophone"/>
 *    </bean>
 *
 * 解析器注册：
 * beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver()) 注册语言解析器，便可以对SpEL进行解析
 *
 * 解析调用：
 *   spring在bean进行初始化的时候会进行属性填充，调用 {@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues)}
 *   在这个函数中会通过构造BeanDefinitionValueResolver类型实例 valueResolver来进行属性值得解析。
 *   通过调用 {@link org.springframework.beans.factory.support.AbstractBeanFactory#evaluateBeanDefinitionString(String, BeanDefinition)} 完成SpEL的解析
 * Strategy interface for resolving a value through evaluating it
 * as an expression, if applicable.
 *
 * <p>A raw {@link org.springframework.beans.factory.BeanFactory} does not
 * contain a default implementation of this strategy. However,
 * {@link org.springframework.context.ApplicationContext} implementations
 * will provide expression support out of the box.
 *
 * @author Juergen Hoeller
 * @since 3.0
 */
public interface BeanExpressionResolver {

	/**
	 * Evaluate the given value as an expression, if applicable;
	 * return the value as-is otherwise.
	 * @param value the value to check
	 * @param evalContext the evaluation context
	 * @return the resolved value (potentially the given value as-is)
	 * @throws BeansException if evaluation failed
	 */
	@Nullable
	Object evaluate(@Nullable String value, BeanExpressionContext evalContext) throws BeansException;

}
