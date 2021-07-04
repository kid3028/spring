package org.springframework.learn.generic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;

public class GenericAPIDemo {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			// 原生类型 primitiveType: int long float
			Class<?> intClass = int.class;
			System.out.println(intClass); // int

			// 数组类型 arrayType: int[] Object[]
			Class<?> arrayClass = Object[].class;
			System.out.println(arrayClass); // class [Ljava.lang.Object;

			// 原始类型 rawType:
			Class<?> rawClass = String.class;
			System.out.println(rawClass); // class java.lang.String

			System.out.println(ArrayList.class); // class java.util.ArrayList
			ParameterizedType parameterizedType = (ParameterizedType) ArrayList.class.getGenericSuperclass();
			System.out.println(parameterizedType); // class java.util.AbstractList
			System.out.println(parameterizedType.getRawType()); // java.util.AbstractList<E>
			Arrays.stream(parameterizedType.getActualTypeArguments()).forEach(System.out::println); // E

		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
