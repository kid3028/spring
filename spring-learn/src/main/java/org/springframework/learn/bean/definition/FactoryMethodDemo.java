package org.springframework.learn.bean.definition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class FactoryMethodDemo {
	public static void main(String[] args) {
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(FactoryMethodDemo.class);
//
//		ctx.refresh();
//		BeanDefinitionBuilder bd = BeanDefinitionBuilder.rootBeanDefinition(User.class);
//		BeanDefinitionReaderUtils.registerWithGeneratedName(bd.getBeanDefinition(), ctx);
//		System.out.println(ctx.getBean(User.class));

//		1626054207160
//		2147483647

		System.out.println(Integer.MAX_VALUE);

	}


	static class User {
		private Integer id = 11;

		public static void init()  {
			System.out.println("init");
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "User{" +
					"id=" + id +
					'}';
		}
	}
}
