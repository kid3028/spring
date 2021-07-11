package org.springframework.learn.lifecycle;

import org.springframework.context.Lifecycle;

public class MyLifecycle implements Lifecycle {
	private boolean running = false;

	@Override
	public void start() {
		this.running = true;
		System.out.println("MyLifecycle 启动...");
	}

	@Override
	public void stop() {
		this.running = false;
		System.out.println("MyLifecycle 停止...");
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
}
