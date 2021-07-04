package org.springframework.learn.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomSpringEventDemo implements ApplicationEventPublisherAware, ApplicationContextAware {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(CustomSpringEventDemo.class, MyListener.class);
		ctx.refresh();
		ctx.publishEvent(new MyEvent("customEvent-1 : Hello World"));
		CustomSpringEventDemo demo = ctx.getBean(CustomSpringEventDemo.class);
		demo.applicationEventPublisher.publishEvent(new MyEvent("customEvent-3 : Hello World"));
		demo.applicationContext.publishEvent(new MyEvent("customEvent-5 : Hello World"));

		ctx.close();
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		applicationEventPublisher.publishEvent(new MyEvent("customEvent-2 : Hello World"));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		applicationContext.publishEvent(new MyEvent("customEvent-4 : Hello World"));
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
			return (String)super.getSource();
		}

		public String getMessage() {
			return getSource();
		}
	}

	static class MyListener implements ApplicationListener<MyEvent> {

		@Override
		public void onApplicationEvent(MyEvent event) {
			System.out.println("收到MyEvent : " + event);
		}
	}


}
