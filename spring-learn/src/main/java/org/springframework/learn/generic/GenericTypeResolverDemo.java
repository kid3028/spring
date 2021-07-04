package org.springframework.learn.generic;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericTypeResolverDemo {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void main(String[] args) {
		try {

			displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getString");
			displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getList");
			displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getStringList");
			Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(StringList.class);
			typeVariableMap.forEach((k, v) -> {
				System.out.println("k = " + k + ", v = " + v);
			});

//			GenericTypeResolverDemo.resolveReturnType(getString, GenericTypeResolverDemo) = class java.lang.String
//			GenericTypeResolverDemo.resolveReturnTypeArgument(getString, GenericTypeResolverDemo) = null

//			GenericTypeResolverDemo.resolveReturnType(getList, GenericTypeResolverDemo) = class java.util.ArrayList
//			GenericTypeResolverDemo.resolveReturnTypeArgument(getList, GenericTypeResolverDemo) = null

//			GenericTypeResolverDemo.resolveReturnType(getStringList, GenericTypeResolverDemo) = class org.springframework.learn.generic.GenericTypeResolverDemo$StringList
//			GenericTypeResolverDemo.resolveReturnTypeArgument(getStringList, GenericTypeResolverDemo) = class java.lang.String

//			k = E, v = class java.lang.String
//			k = T, v = class java.lang.String
//			k = E, v = class java.lang.String
//			k = E, v = class java.lang.String
//			k = E, v = class java.lang.String
//			k = E, v = class java.lang.String
		}catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static StringList getStringList() {
		return null;
	}

	/**
	 * 参数类型变量没有具体化，null
	 * @return
	 */
	public static ArrayList<?> getList() {
		return null;
	}

	public static String getString() {
		return null;
	}

	private static void  displayReturnTypeGenericInfo(Class<?> containingClass, Class<?> genericIfc, String methodName) throws NoSuchMethodException {
		Method method = containingClass.getMethod(methodName);
		Class<?> returnType = GenericTypeResolver.resolveReturnType(method, containingClass);
		System.out.printf("GenericTypeResolverDemo.resolveReturnType(%s, %s) = %s \n", methodName, containingClass.getSimpleName(), returnType);
		Class<?> returnTypeArgument = GenericTypeResolver.resolveReturnTypeArgument(method, genericIfc);
		System.out.printf("GenericTypeResolverDemo.resolveReturnTypeArgument(%s, %s) = %s \n", methodName, containingClass.getSimpleName(), returnTypeArgument);
	}

}
