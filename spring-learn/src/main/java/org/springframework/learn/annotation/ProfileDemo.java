package org.springframework.learn.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

public class ProfileDemo {

	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(ProfileDemo.class);
			// 获取Environment对象，可配置
			ConfigurableEnvironment env = ctx.getEnvironment();
			env.setDefaultProfiles("odd"); // 设置默认的profile为odd
			env.setActiveProfiles("even"); // 设置激活的profile为even
			ctx.refresh();
			System.out.println(ctx.getBean("number", Integer.class));
			ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean("number")
	@Profile("odd")
	public Integer odd() {
		return 1;
	}

	@Bean("number")
//	@Profile("even")
	@Conditional(EvenCondition.class)
	public Integer even() {
		return 2;
	}
}
