package org.springframework.learn.ioc.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.learn.ioc.domain.User;

import java.util.concurrent.TimeUnit;

public class ThreadLocalScopeDemo {

	@Bean
	@Scope(CustomThreadLocalScope.SCOPE_NAME)
	public User user() {
		return User.createUser();
	}

	public static void main(String[] args) throws InterruptedException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ThreadLocalScopeDemo.class);
//		ctx.getBeanFactory().registerScope(CustomThreadLocalScope.SCOPE_NAME, new CustomThreadLocalScope());
		ctx.addBeanFactoryPostProcessor(beanFactory -> {
			beanFactory.registerScope(CustomThreadLocalScope.SCOPE_NAME, new CustomThreadLocalScope());
		});
		ctx.refresh();

		ThreadLocalScopeDemo demo = ctx.getBean(ThreadLocalScopeDemo.class);
		Thread t1 = new Thread(() -> {
			System.out.println(ctx.getBean(User.class).hashCode());
		});
		t1.start();
		System.out.println(ctx.getBean(User.class).hashCode());

		TimeUnit.SECONDS.sleep(2);


	}


}
