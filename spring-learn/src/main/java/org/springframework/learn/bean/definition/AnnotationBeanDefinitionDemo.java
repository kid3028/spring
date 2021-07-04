package org.springframework.learn.bean.definition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.learn.ioc.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Import(AnnotationBeanDefinitionDemo.Config.class) // 3、通过 @Import 方式导入
public class AnnotationBeanDefinitionDemo {

	public static void main(String[] args) {
		// 创建 BeanFactory 容器
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		// 注册 Configuration class(配置类)
		ctx.register(Config.class);
		// 1、通过 @Bean 方式定义
		// 2、通过 @Component 方式
		// 3、通过 @Import 导入

		ctx.refresh();

		apiRegisterBeanDefinition(ctx, "api-user");
		apiRegisterBeanDefinition(ctx, null);

		System.out.println("所有 Config 类型的bean : " + ctx.getBeansOfType(Config.class));
		System.out.println("所有 User 类型的bean : " + ctx.getBeansOfType(User.class));


		ctx.close();
	}


	public static void apiRegisterBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
		builder.addPropertyValue("id", 11).addPropertyValue("name", "api register beanDefinition");

		// java api 命名注册beanDefinition
		if (StringUtils.hasText(beanName)) {
			registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
		}else {
			// java api 非命名注册侧BeanDefinition
			BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(), registry);
		}
	}

	// 2、通过 @Component 方式
	@Component  // 定义当前类为 Spring Bean (组件)
	public static class Config {

		// 1、通过 @Bean 方式定义
		/**
		 * 通过java注解的方式定义bean
		 * @return
		 */
		@Bean
		public User user() {
			User user = new User();
			user.setId(11);
			user.setName("spring annotation beanDefinition");
			return user;
		}
	}
}

