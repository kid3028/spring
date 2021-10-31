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

package org.springframework.context.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;

/**
 * 标记一个方法作为{@link ApplicationListener}
 *
 * 如果被标记的方法只支持单个事件类型，方法声明一个与eventType对应的的参数即可
 * 如果方法支持多个事件类型，在 {@code EventListener{classes={xxx, xxx...}}}列出支持的event类型即可
 *
 * Event可以是ApplicationEvent也可以是任意类型
 *
 * {@code @EventListener}注解是通过{@link EventListenerMethodProcessor}进行处理，
 * 并完成自动注册
 * 被注解的方法可能有一个非空返回值，这样的情况下，方法调用的返回值将作为一个新的时间发布出去；
 * 如果返回值是一个数组或者集合，那么每一个元素都将被作为自定义事件发布出去。
 *
 * listener调用的顺序是可以指定的，将{@link Order @Order} {@link EventListener @EventListener} 一起使用即可
 *
 * listener可以抛出任意类型的异常，受检查异常将会被包装成{@link java.lang.reflect.UndeclaredThrowableException}
 * 因为事件发布器只能处理运行时异常
 *
 * Annotation that marks a method as a listener for application events.
 *
 * <p>If an annotated method supports a single event type, the method may
 * declare a single parameter that reflects the event type to listen to.
 * If an annotated method supports multiple event types, this annotation
 * may refer to one or more supported event types using the {@code classes}
 * attribute. See the {@link #classes} javadoc for further details.
 *
 * <p>Events can be {@link ApplicationEvent} instances as well as arbitrary
 * objects.
 *
 * <p>Processing of {@code @EventListener} annotations is performed via
 * the internal {@link EventListenerMethodProcessor} bean which gets
 * registered automatically when using Java config or manually via the
 * {@code <context:annotation-config/>} or {@code <context:component-scan/>}
 * element when using XML config.
 *
 * <p>Annotated methods may have a non-{@code void} return type. When they
 * do, the result of the method invocation is sent as a new event. If the
 * return type is either an array or a collection, each element is sent
 * as a new individual event.
 *
 * <p>It is also possible to define the order in which listeners for a
 * certain event are to be invoked. To do so, add Spring's common
 * {@link Order @Order} annotation
 * alongside this event listener annotation.
 *
 * <p>While it is possible for an event listener to declare that it
 * throws arbitrary exception types, any checked exceptions thrown
 * from an event listener will be wrapped in an
 * {@link java.lang.reflect.UndeclaredThrowableException}
 * since the event publisher can only handle runtime exceptions.
 *
 * @author Stephane Nicoll
 * @since 4.2
 * @see EventListenerMethodProcessor
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {

	/**
	 * Alias for {@link #classes}.
	 */
	@AliasFor("classes")
	Class<?>[] value() default {};

	/**
	 * The event classes that this listener handles.
	 * <p>If this attribute is specified with a single value, the
	 * annotated method may optionally accept a single parameter.
	 * However, if this attribute is specified with multiple values,
	 * the annotated method must <em>not</em> declare any parameters.
	 */
	@AliasFor("value")
	Class<?>[] classes() default {};

	/**
	 * Spring Expression Language (SpEL) attribute used for making the
	 * event handling conditional.
	 * <p>Default is {@code ""}, meaning the event is always handled.
	 * <p>The SpEL expression evaluates against a dedicated context that
	 * provides the following meta-data:
	 * <ul>
	 * <li>{@code #root.event}, {@code #root.args} for
	 * references to the {@link ApplicationEvent} and method arguments
	 * respectively.</li>
	 * <li>Method arguments can be accessed by index. For instance the
	 * first argument can be accessed via {@code #root.args[0]}, {@code #p0}
	 * or {@code #a0}. Arguments can also be accessed by name if that
	 * information is available.</li>
	 * </ul>
	 */
	String condition() default "";

}
