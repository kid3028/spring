package org.springframework.learn.ioc.metadata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.MapPropertySource;
import org.springframework.learn.ioc.domain.User;

import java.util.HashMap;
import java.util.Map;

@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
@Import(User.class)  // import将导入类做为配置类
@PropertySource("classpath:/META-INF/user.properties") // java8 @Repeatable
@PropertySource("classpath:/META-INF/user.properties")
// 等价于
//@PropertySources({@PropertySource("a"), @PropertySource("b")})
public class AnnotatedContainerMetadataConfigurationDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		HashMap<String, Object> map = new HashMap<>();
		map.put("user.name", "阿华哥");
		// 扩展Environment中的PropertySources
		// 添加PropertySource操作必须在refresh方法之前完成
		ctx.getEnvironment().getPropertySources().addFirst(new MapPropertySource("first-map-propertySource", map));

		ctx.register(AnnotatedContainerMetadataConfigurationDemo.class);
		ctx.refresh();

		System.out.println(ctx.getEnvironment().getPropertySources());
		Map<String, User> users = ctx.getBeansOfType(User.class);
		users.forEach((beanName, user) -> System.out.println("beanName : " + beanName + ", user : " + user));
		ctx.close();
	}

	@Bean
	public User configUser(@Value("${user.id}")Integer id, @Value("${user.name}") String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		return user;
	}
}
