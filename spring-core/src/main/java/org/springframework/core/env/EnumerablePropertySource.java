/*
 * Copyright 2002-2020 the original author or authors.
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

package org.springframework.core.env;

import org.springframework.util.ObjectUtils;

/**
 *
 * 一个PropertySource实现，能够查询其底层源对象以枚举所有可能的属性名称/值对。公开getPropertyNames（）方法，
 * 以允许调用方内省可用属性，而无需访问底层源对象。这也有助于更高效地实现containsProperty（String），因为它可以调用getPropertyNames（）
 * 并遍历返回的数组，而不是尝试调用可能更昂贵的getProperty（String）。实现可以考虑缓存GePrimeTyNeScript（）的结果，以充分利用此性能机会。
 *
 * {@link PropertySource}实现，通过查询底层元数据来枚举所有可能的 propertyName-value。
 * 提供了{@link #getProperty(String)}方法允许调用方内省可用的属性，而无需访问底层元数据。
 * {@link #containsProperty(String)}方法提供了更加便捷的方式，内部调用{@link #getPropertyNames()}
 * 方法，然后遍历返回的数组对象，相比较而言{@link #getProperty(String)}就显得比较昂贵.
 *
 * A {@link PropertySource} implementation capable of interrogating its
 * underlying source object to enumerate all possible property name/value
 * pairs. Exposes the {@link #getPropertyNames()} method to allow callers
 * to introspect available properties without having to access the underlying
 * source object. This also facilitates a more efficient implementation of
 * {@link #containsProperty(String)}, in that it can call {@link #getPropertyNames()}
 * and iterate through the returned array rather than attempting a call to
 * {@link #getProperty(String)} which may be more expensive. Implementations may
 * consider caching the result of {@link #getPropertyNames()} to fully exploit this
 * performance opportunity.
 *
 * <p>Most framework-provided {@code PropertySource} implementations are enumerable;
 * a counter-example would be {@code JndiPropertySource} where, due to the
 * nature of JNDI it is not possible to determine all possible property names at
 * any given time; rather it is only possible to try to access a property
 * (via {@link #getProperty(String)}) in order to evaluate whether it is present
 * or not.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @param <T> the source type
 */
public abstract class EnumerablePropertySource<T> extends PropertySource<T> {

	/**
	 * Create a new {@code EnumerablePropertySource} with the given name and source object.
	 * @param name the associated name
	 * @param source the source object
	 */
	public EnumerablePropertySource(String name, T source) {
		super(name, source);
	}

	/**
	 * Create a new {@code EnumerablePropertySource} with the given name and with a new
	 * {@code Object} instance as the underlying source.
	 * @param name the associated name
	 */
	protected EnumerablePropertySource(String name) {
		super(name);
	}


	/**
	 * Return whether this {@code PropertySource} contains a property with the given name.
	 * <p>This implementation checks for the presence of the given name within the
	 * {@link #getPropertyNames()} array.
	 * @param name the name of the property to find
	 */
	@Override
	public boolean containsProperty(String name) {
		return ObjectUtils.containsElement(getPropertyNames(), name);
	}

	/**
	 * Return the names of all properties contained by the
	 * {@linkplain #getSource() source} object (never {@code null}).
	 */
	public abstract String[] getPropertyNames();

}
