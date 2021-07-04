package org.springframework.learn.ioc.dependency.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.learn.ioc.annotation.InjectUser;
import org.springframework.learn.ioc.annotation.MyAutowired;
import org.springframework.learn.ioc.domain.User;

import java.util.Map;

public class AnnotationAutowiredFlowDemo {

	@Autowired
	private User user;  // DependencyDescriptor ->
	                   // 必须(required = true) + 实时注入(eager=true) +
	                  // 通过类型依赖查找(User.class) + 字段名称(user)

	@Autowired
	private Map<String, User> users;


	@Autowired
	@Lazy
	private User lazyUser;

	/**
	 * 通过继承形式扩展@Autowired
	 */
	@MyAutowired
	private User myAutowiredUser;

	@InjectUser
	private User injectUser;


	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
		reader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
		ctx.register(AnnotationAutowiredFlowDemo.class);
		ctx.refresh();

		AnnotationAutowiredFlowDemo demo = ctx.getBean(AnnotationAutowiredFlowDemo.class);
		System.out.println("user : " + demo.user);
		System.out.println("users : " + demo.users);
		System.out.println("lazyUser : " + demo.lazyUser);
		System.out.println("myAutowiredUser : " + demo.myAutowiredUser);
		System.out.println("injectUser : " + demo.injectUser);
		ctx.close();
	}

	/**
	 * 方法一
	 * org.springframework.context.annotation.AnnotationConfigUtils#AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME
	 * org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#AutowiredAnnotationBeanPostProcessor()
	 * static ： 如果想让bean提前初始化，可以将方法设置为静态的
	 * @return
	 */
//	@Bean(name = AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
//	public static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
//		AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
//		Set<Class<? extends Annotation>> autowiredTypes = new LinkedHashSet<>();
//		autowiredTypes.add(Autowired.class);
//		autowiredTypes.add(Value.class);
//		autowiredTypes.add(MyAutowired.class);
//		autowiredTypes.add(InjectUser.class);
//		processor.setAutowiredAnnotationTypes(autowiredTypes);
//		return processor;
//	}

	/**
	 * 方法二
	 * @return
	 */
	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE - 3)
	public static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
		AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
		processor.setAutowiredAnnotationType(InjectUser.class);
		return processor;
	}
}
