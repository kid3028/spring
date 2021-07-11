package org.springframework.learn.ioc.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class CircularReferenceDemo {

	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(CircularReferenceDemo.class);
			// false  Is there an unresolvable circular reference?
//			ctx.setAllowCircularReferences(false);
			ctx.refresh();
			System.out.println(ctx.getBean(A.class));
			System.out.println(ctx.getBean(B.class));
		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Bean
	public A a(){
		A a = new A();
		a.name = "a";
		return a;
	}

	@Bean
	public B b(){
		B b = new B();
		b.name = "b";
		return b;
	}

	static class A {
		@Autowired
		private B b;

		private String name;

		public B getB() {
			return b;
		}

		public void setB(B b) {
			this.b = b;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "A{" +
					"b=" + b.name +
					", name='" + name + '\'' +
					'}';
		}
	}


	static class B {
		@Autowired
		private A a;

		private String name;

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "B{" +
					"a=" + a.name +
					", name='" + name + '\'' +
					'}';
		}
	}
}
