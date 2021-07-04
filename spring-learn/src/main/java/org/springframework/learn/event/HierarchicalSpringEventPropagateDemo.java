package org.springframework.learn.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.HashSet;
import java.util.Set;

public class HierarchicalSpringEventPropagateDemo {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
		parentContext.setId("parent-context");
		parentContext.register(MyApplicationListener.class);

		AnnotationConfigApplicationContext currentContext = new AnnotationConfigApplicationContext();
		currentContext.setId("current-context");
		currentContext.setParent(parentContext);
		currentContext.register(MyApplicationListener.class);

		// parent.refresh会触发自己
		parentContext.refresh();
		// current.refresh会触发自己和parent
		currentContext.refresh();
		currentContext.close();
		parentContext.close();

	}

	static class MyApplicationListener implements ApplicationListener<ApplicationContextEvent> {

		/**同一个事件不会在上下文重复触发
		 * 对事件进行去重，
		 */
		private static Set<ApplicationContextEvent> events = new HashSet<>();

		@Override
		public void onApplicationEvent(ApplicationContextEvent event) {
			if (events.add(event)) {
				System.out.println("监听到【" + event.getApplicationContext().getId() + "】事件 : " + event.getClass().getSimpleName());
			}
		}
	}

}
