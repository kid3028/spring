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

package org.springframework.core.type;

import org.springframework.lang.Nullable;

/**
 * 访问类元信息的抽象接口
 * Interface that defines abstract metadata of a specific class,
 * in a form that does not require that class to be loaded yet.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see StandardClassMetadata
 * @see org.springframework.core.type.classreading.MetadataReader#getClassMetadata()
 * @see AnnotationMetadata
 */
public interface ClassMetadata {

	/**
	 * 获取class名称
	 * Return the name of the underlying class.
	 */
	String getClassName();

	/**
	 * class是否是一个接口
	 * Return whether the underlying class represents an interface.
	 */
	boolean isInterface();

	/**
	 * class是否是一个注解
	 * Return whether the underlying class represents an annotation.
	 * @since 4.1
	 */
	boolean isAnnotation();

	/**
	 * class是否是抽象类
	 * Return whether the underlying class is marked as abstract.
	 */
	boolean isAbstract();

	/**
	 * class是否是一个具体类
	 * Return whether the underlying class represents a concrete class,
	 * i.e. neither an interface nor an abstract class.
	 */
	boolean isConcrete();

	/**
	 * class是否是final
	 * Return whether the underlying class is marked as 'final'.
	 */
	boolean isFinal();

	/**
	 * class是否是独立的，如：是一个顶级类、嵌套类，可以独立于封闭类构造
	 * Determine whether the underlying class is independent, i.e. whether
	 * it is a top-level class or a nested class (static inner class) that
	 * can be constructed independently from an enclosing class.
	 */
	boolean isIndependent();

	/**
	 * class是否是一个封闭类，如class是一个内部类或者嵌套类
	 * 如果class是一个顶级类，方法将会返回false
	 * Return whether the underlying class is declared within an enclosing
	 * class (i.e. the underlying class is an inner/nested class or a
	 * local class within a method).
	 * <p>If this method returns {@code false}, then the underlying
	 * class is a top-level class.
	 */
	boolean hasEnclosingClass();

	/**
	 * 如果class是一个封闭类，将返回类名
	 * 如果class是一个顶级类，将返回null
	 * Return the name of the enclosing class of the underlying class,
	 * or {@code null} if the underlying class is a top-level class.
	 */
	@Nullable
	String getEnclosingClassName();

	/**
	 * class是否有父类
	 * Return whether the underlying class has a super class.
	 */
	boolean hasSuperClass();

	/**
	 * 获取父类的名称，如果没有父类，返回null
	 * Return the name of the super class of the underlying class,
	 * or {@code null} if there is no super class defined.
	 */
	@Nullable
	String getSuperClassName();

	/**
	 * class实现的接口列表，如果没有返回一个空数组
	 * Return the names of all interfaces that the underlying class
	 * implements, or an empty array if there are none.
	 */
	String[] getInterfaceNames();

	/**
	 * 返回当前class所有成员属性的className
	 * 所有可见性的属性都返回
	 * 不返回父类的属性
	 * Return the names of all classes declared as members of the class represented by
	 * this ClassMetadata object. This includes public, protected, default (package)
	 * access, and private classes and interfaces declared by the class, but excludes
	 * inherited classes and interfaces. An empty array is returned if no member classes
	 * or interfaces exist.
	 * @since 3.1
	 */
	String[] getMemberClassNames();

}
