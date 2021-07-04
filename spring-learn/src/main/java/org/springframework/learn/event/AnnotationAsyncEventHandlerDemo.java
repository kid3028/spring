package org.springframework.learn.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AnnotationAsyncEventHandlerDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AnnotationAsyncEventHandlerDemo.class);
		ctx.refresh();
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


	@EventListener
	@Async
	public void onApplicationEvent(MyEvent event) {
		System.out.println("线程 : " + Thread.currentThread() + " 收到事件 : " + event.getMessage());
	}

	/**
	 * 信息: No task executor bean found for async processing: no bean of type TaskExecutor and no bean named 'taskExecutor' either
	 */
	@Bean
	public Executor taskExecutor() {
		return Executors.newSingleThreadExecutor(new CustomizableThreadFactory("taskExecutor-"));
	}

}

