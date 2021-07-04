package org.springframework.learn.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncEventHandlerDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AsyncEventHandlerDemo.class, MyListener.class);
		ctx.addApplicationListener((MyEvent event) -> {
			throw new RuntimeException("事件处理异常会被ErrorHandler处理");
		});
		ctx.refresh();
		ApplicationEventMulticaster multicaster = ctx.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
		if (multicaster instanceof SimpleApplicationEventMulticaster) {
			SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = (SimpleApplicationEventMulticaster) multicaster;
			ExecutorService executor = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("eventTask"));
			simpleApplicationEventMulticaster.setTaskExecutor(executor);
			// 监听close事件，优雅关闭线程池，否则程序不会退出
			ctx.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
				if (!executor.isShutdown()) {
					executor.shutdown();
				}
			});
			simpleApplicationEventMulticaster.setErrorHandler((e) -> {
				System.out.println("事件处理异常 : " + e.getMessage());
			});

		}

		ctx.publishEvent(new MyEvent("Hello World"));
		ctx.close();
	}

	@SuppressWarnings("serial")
	static class MyEvent extends ApplicationEvent {
		/**
		 * Create a new ApplicationEvent.
		 *
		 * @param source the object on which the event initially occurred (never {@code null})
		 */
		public MyEvent(String source) {
			super(source);
		}

		@Override
		public String getSource() {
			return (String) super.getSource();
		}

		public String getMessage() {
			return getSource();
		}
	}

	static class MyListener implements ApplicationListener<MyEvent> {

		@Override
		public void onApplicationEvent(MyEvent event) {
			System.out.println("线程 : " + Thread.currentThread() + " 收到事件 : " + event.getMessage());
		}
	}

}

