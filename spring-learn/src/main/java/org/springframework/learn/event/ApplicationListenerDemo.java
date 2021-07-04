package org.springframework.learn.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync  // @Async 需要结合 @EnableAsync才能实现异步
public class ApplicationListenerDemo implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		// 注册事件方法一：基于spring注解 @EventListener向spring应用上下文注册事件
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		// 注册事件方法二： 基于spring接口：向spring应用上下文注册事件
		// 注册listener方法一：基于ConfigurableApplicationContext api实现
//		GenericApplicationContext ctx = new GenericApplicationContext();
		ctx.addApplicationListener(new ApplicationListener() {
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				System.out.println("ApplicationEvent 接收到spring事件 : " + event);
			}
		});
		ctx.register(ApplicationListenerDemo.class);
		// 注册listener方法二：基于ApplicationListener注册为SpringBean
		ctx.register(MyApplicationListener.class);

		ctx.refresh();
		ctx.start();
		ctx.close();
	}

	@Override
	@SuppressWarnings("serial")
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
		this.applicationEventPublisher.publishEvent(new ApplicationEvent("========Hello World========="){});
		this.applicationEventPublisher.publishEvent("~~~~~~~~Hello World~~~~~~~~");
	}

	static class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

		@Override
		public void onApplicationEvent(ContextRefreshedEvent event) {
			System.out.println("MyApplicationListener接收到spring事件 : " + event);
		}
	}


	@EventListener
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("@EventListener接口到spring事件 : " + event);
	}

//	@EventListener
//	@Async
//	public void onContextRefreshedEvent(ContextRefreshedEvent event) {
//		System.out.println("thread- " + Thread.currentThread() + " @EventListener接收到spring ContextRefreshedEvent : " + event);
//	}
//
//	@EventListener
//	@Order(1)
//	public void onContextRefreshedEvent1(ContextRefreshedEvent event) {
//		System.out.println("1 - @EventListener接收到spring ContextRefreshedEvent : " + event);
//	}
//
//	@EventListener
//	@Order(-2)
//	public void onContextRefreshedEvent2(ContextRefreshedEvent event) {
//		System.out.println("2 - @EventListener接收到spring ContextRefreshedEvent : " + event);
//	}
//
//	@EventListener
//	public void onContextStartedEvent(ApplicationEvent event) {
//		System.out.println("@EventListener接收到spring ContextStartedEvent : " + event);
//	}
//
//	@EventListener
//	public void onContextClosedEvent(ApplicationEvent event) {
//		System.out.println("@EventListener接收到spring ContextClosedEvent : " + event);
//	}

}
