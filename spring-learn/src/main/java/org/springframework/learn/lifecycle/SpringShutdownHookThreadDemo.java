package org.springframework.learn.lifecycle;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

public class SpringShutdownHookThreadDemo {
	public static void main(String[] args) throws IOException {
		GenericApplicationContext ctx = new GenericApplicationContext();
		ctx.addApplicationListener((ContextClosedEvent event) -> {
			System.out.printf("[线程 %s]spring shutdownHookThread ContextClosedEvent 处理 \n",
					Thread.currentThread().getName());
		});

		ctx.refresh();
		// 移除shutdownHookThread会调用doClose()
		ctx.registerShutdownHook();
		System.out.println("按任意键退出");
		System.in.read();
		ctx.close();
	}
}
