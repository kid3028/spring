package org.springframework.learn.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link org.springframework.core.env.Environment} 配置属性源变更
 */
public class EnvironmentPropertySourceChangeDemo {

	@Value("${user.name}")  // 不具备动态更新能力
	private String username;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(EnvironmentPropertySourceChangeDemo.class);
		ConfigurableEnvironment environment = ctx.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		Map<String, Object> map = new HashMap<>();
		map.put("user.name", "阿华哥");
		propertySources.addFirst(new MapPropertySource("first-property-source", map));
		ctx.refresh();
		map.put("user.name", "007");
		for (PropertySource<?> propertySource : propertySources) {
			System.out.println("propertyName : " + propertySource + ", user.name : " + propertySource.getProperty("user.name"));

		}
		EnvironmentPropertySourceChangeDemo demo = ctx.getBean(EnvironmentPropertySourceChangeDemo.class);
		System.out.println(demo.username);
		ctx.close();
	}
}
