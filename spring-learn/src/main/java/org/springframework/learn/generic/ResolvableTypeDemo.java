package org.springframework.learn.generic;

import org.springframework.core.ResolvableType;

public class ResolvableTypeDemo {

	public static void main(String[] args) {
		ResolvableType resolvableType = ResolvableType.forClass(StringList.class);
		System.out.println(resolvableType); // org.springframework.learn.generic.StringList
		System.out.println(resolvableType.getSuperType()); // ArrayList
		System.out.println(resolvableType.getSuperType().getSuperType()); // AbstractList
		System.out.println(resolvableType.asCollection().resolve()); // rawType
		System.out.println(resolvableType.asCollection().resolveGeneric(0)); // String
	}
}
