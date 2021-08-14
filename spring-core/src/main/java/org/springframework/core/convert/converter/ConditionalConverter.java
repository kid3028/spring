/*
 * Copyright 2002-2014 the original author or authors.
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

package org.springframework.core.convert.converter;

import org.springframework.core.convert.TypeDescriptor;

/**
 * 为{@link Converter} {@link GenericConverter} {@link ConverterFactory}添加有条件的执行类型转换.
 *
 * 根据字段or类的注释、方法等特征来判断是否执行自定义的类型转换。
 * 例如，将一个字符串转换为日期字段，如果该字段上标记了{@code @DateTimeFormat}那么conditional条件满足
 *
 * 再比如，将字段串转换为 {@code Account} 类型，如果在 {@code Account}类中存在 {@code public static findAccount(String)}
 * 方法，那么条件也就满足
 *
 * Allows a {@link Converter}, {@link GenericConverter} or {@link ConverterFactory} to
 * conditionally execute based on attributes of the {@code source} and {@code target}
 * {@link TypeDescriptor}.
 *
 * <p>Often used to selectively match custom conversion logic based on the presence of a
 * field or class-level characteristic, such as an annotation or method. For example, when
 * converting from a String field to a Date field, an implementation might return
 * {@code true} if the target field has also been annotated with {@code @DateTimeFormat}.
 *
 * <p>As another example, when converting from a String field to an {@code Account} field,
 * an implementation might return {@code true} if the target Account class defines a
 * {@code public static findAccount(String)} method.
 *
 * @author Phillip Webb
 * @author Keith Donald
 * @since 3.2
 * @see Converter
 * @see GenericConverter
 * @see ConverterFactory
 * @see ConditionalGenericConverter
 */
public interface ConditionalConverter {

	/**
	 * {code sourceType} 能否转换为 {@code targetType}
	 * Should the conversion from {@code sourceType} to {@code targetType} currently under
	 * consideration be selected?
	 * @param sourceType the type descriptor of the field we are converting from
	 * @param targetType the type descriptor of the field we are converting to
	 * @return true if conversion should be performed, false otherwise
	 */
	boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType);

}
