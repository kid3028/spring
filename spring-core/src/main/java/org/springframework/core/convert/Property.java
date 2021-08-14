/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.core.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * JavaBean属性的抽象描述。使用{@link Property}开发者不再需要依赖 {@link java.beans.PropertyDescriptor}
 * {@code java.beans}包在Android等一些环境下是不可用的，所以，它们对于spring的类型转换能力极其迫切。
 *
 * Property被用来构建{@link TypeDescriptor}对象，实现类型转换
 *
 * A description of a JavaBeans Property that allows us to avoid a dependency on
 * {@code java.beans.PropertyDescriptor}. The {@code java.beans} package
 * is not available in a number of environments (e.g. Android, Java ME), so this is
 * desirable for portability of Spring's core conversion facility.
 *
 * <p>Used to build a {@link TypeDescriptor} from a property location. The built
 * {@code TypeDescriptor} can then be used to convert from/to the property type.
 *
 * @author Keith Donald
 * @author Phillip Webb
 * @since 3.1
 * @see TypeDescriptor#TypeDescriptor(Property)
 * @see TypeDescriptor#nested(Property, int)
 */
public final class Property {

	/**
	 * 静态变量
	 * 全局缓存
	 */
	private static Map<Property, Annotation[]> annotationCache = new ConcurrentReferenceHashMap<>();

	/**
	 * 属性所属的类
	 */
	private final Class<?> objectType;

	/**
	 * 属性对应的读方法
	 */
	@Nullable
	private final Method readMethod;

	/**
	 * 属性对应的写方法
	 */
	@Nullable
	private final Method writeMethod;

	/**
	 * 属性的名称
	 */
	private final String name;

	/**
	 * 属性类型
	 */
	private final MethodParameter methodParameter;

	/**
	 * 属性上标记的注解？？
	 */
	@Nullable
	private Annotation[] annotations;


	public Property(Class<?> objectType, @Nullable Method readMethod, @Nullable Method writeMethod) {
		this(objectType, readMethod, writeMethod, null);
	}

	public Property(
			Class<?> objectType, @Nullable Method readMethod, @Nullable Method writeMethod, @Nullable String name) {

		this.objectType = objectType;
		this.readMethod = readMethod;
		this.writeMethod = writeMethod;
		this.methodParameter = resolveMethodParameter();
		this.name = (name != null ? name : resolveName());
	}


	/**
	 * 声明该属性的class
	 * 可能来自于父类
	 * The object declaring this property, either directly or in a superclass the object extends.
	 */
	public Class<?> getObjectType() {
		return this.objectType;
	}

	/**
	 * 属性的名称
	 * The name of the property: e.g. 'foo'
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 属性类型
	 * The property type: e.g. {@code java.lang.String}
	 */
	public Class<?> getType() {
		return this.methodParameter.getParameterType();
	}

	/**
	 * 属性读方法
	 * The property getter method: e.g. {@code getFoo()}
	 */
	@Nullable
	public Method getReadMethod() {
		return this.readMethod;
	}

	/**
	 * 属性写方法
	 * The property setter method: e.g. {@code setFoo(String)}
	 */
	@Nullable
	public Method getWriteMethod() {
		return this.writeMethod;
	}


	// package private

	MethodParameter getMethodParameter() {
		return this.methodParameter;
	}

	Annotation[] getAnnotations() {
		if (this.annotations == null) {
			this.annotations = resolveAnnotations();
		}
		return this.annotations;
	}


	// internal helpers

	private String resolveName() {
		if (this.readMethod != null) {
			int index = this.readMethod.getName().indexOf("get");
			if (index != -1) {
				index += 3;
			}
			else {
				index = this.readMethod.getName().indexOf("is");
				if (index == -1) {
					throw new IllegalArgumentException("Not a getter method");
				}
				index += 2;
			}
			return StringUtils.uncapitalize(this.readMethod.getName().substring(index));
		}
		else if (this.writeMethod != null) {
			int index = this.writeMethod.getName().indexOf("set");
			if (index == -1) {
				throw new IllegalArgumentException("Not a setter method");
			}
			index += 3;
			return StringUtils.uncapitalize(this.writeMethod.getName().substring(index));
		}
		else {
			throw new IllegalStateException("Property is neither readable nor writeable");
		}
	}

	private MethodParameter resolveMethodParameter() {
		MethodParameter read = resolveReadMethodParameter();
		MethodParameter write = resolveWriteMethodParameter();
		if (write == null) {
			if (read == null) {
				throw new IllegalStateException("Property is neither readable nor writeable");
			}
			return read;
		}
		if (read != null) {
			Class<?> readType = read.getParameterType();
			Class<?> writeType = write.getParameterType();
			if (!writeType.equals(readType) && writeType.isAssignableFrom(readType)) {
				return read;
			}
		}
		return write;
	}

	@Nullable
	private MethodParameter resolveReadMethodParameter() {
		if (getReadMethod() == null) {
			return null;
		}
		return resolveParameterType(new MethodParameter(getReadMethod(), -1));
	}

	@Nullable
	private MethodParameter resolveWriteMethodParameter() {
		if (getWriteMethod() == null) {
			return null;
		}
		return resolveParameterType(new MethodParameter(getWriteMethod(), 0));
	}

	private MethodParameter resolveParameterType(MethodParameter parameter) {
		// needed to resolve generic property types that parameterized by sub-classes e.g. T getFoo();
		GenericTypeResolver.resolveParameterType(parameter, getObjectType());
		return parameter;
	}

	private Annotation[] resolveAnnotations() {
		Annotation[] annotations = annotationCache.get(this);
		if (annotations == null) {
			Map<Class<? extends Annotation>, Annotation> annotationMap = new LinkedHashMap<>();
			addAnnotationsToMap(annotationMap, getReadMethod());
			addAnnotationsToMap(annotationMap, getWriteMethod());
			addAnnotationsToMap(annotationMap, getField());
			annotations = annotationMap.values().toArray(new Annotation[0]);
			annotationCache.put(this, annotations);
		}
		return annotations;
	}

	private void addAnnotationsToMap(
			Map<Class<? extends Annotation>, Annotation> annotationMap, @Nullable AnnotatedElement object) {

		if (object != null) {
			for (Annotation annotation : object.getAnnotations()) {
				annotationMap.put(annotation.annotationType(), annotation);
			}
		}
	}

	@Nullable
	private Field getField() {
		String name = getName();
		if (!StringUtils.hasLength(name)) {
			return null;
		}
		Field field = null;
		Class<?> declaringClass = declaringClass();
		if (declaringClass != null) {
			field = ReflectionUtils.findField(declaringClass, name);
			if (field == null) {
				// Same lenient fallback checking as in CachedIntrospectionResults...
				field = ReflectionUtils.findField(declaringClass, StringUtils.uncapitalize(name));
				if (field == null) {
					field = ReflectionUtils.findField(declaringClass, StringUtils.capitalize(name));
				}
			}
		}
		return field;
	}

	@Nullable
	private Class<?> declaringClass() {
		if (getReadMethod() != null) {
			return getReadMethod().getDeclaringClass();
		}
		else if (getWriteMethod() != null) {
			return getWriteMethod().getDeclaringClass();
		}
		else {
			return null;
		}
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Property)) {
			return false;
		}
		Property otherProperty = (Property) other;
		return (ObjectUtils.nullSafeEquals(this.objectType, otherProperty.objectType) &&
				ObjectUtils.nullSafeEquals(this.name, otherProperty.name) &&
				ObjectUtils.nullSafeEquals(this.readMethod, otherProperty.readMethod) &&
				ObjectUtils.nullSafeEquals(this.writeMethod, otherProperty.writeMethod));
	}

	@Override
	public int hashCode() {
		return (ObjectUtils.nullSafeHashCode(this.objectType) * 31 + ObjectUtils.nullSafeHashCode(this.name));
	}

}
