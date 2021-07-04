package org.springframework.learn.conversion;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.learn.ioc.domain.User;

public class CustomPropertyEditorDemo {
	public static void main(String[] args) {
		try {

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/custom-property-editor-context.xml");
			System.out.println(ctx.getBean(User.class));

		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
