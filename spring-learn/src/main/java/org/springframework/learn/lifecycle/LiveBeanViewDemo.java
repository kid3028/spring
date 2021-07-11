package org.springframework.learn.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.LiveBeansView;

/**
 * spring bean桥接到JMX，看视频理解
 */
public class LiveBeanViewDemo {
	public static void main(String[] args) {
		// 添加LiveBeansView的ObjectName的domain
		System.setProperty(LiveBeansView.MBEAN_DOMAIN_PROPERTY_NAME, "org.springframework.learn");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(LiveBeanViewDemo.class);
		ctx.refresh();
	}
}
