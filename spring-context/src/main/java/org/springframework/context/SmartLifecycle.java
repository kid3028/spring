/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context;

/**
 * {@link Lifecycle}的扩展接口
 *
 * {@link #isAutoStartup()}对象是否应该在应用上下文刷新的同时进行启动
 * {@link #stop(Runnable)}对于有异步关闭的对象是非常实用的
 * 该接口的任何实现都必须在关闭完成时调用{@code run()}方法，以避免在整个{@link ApplicationContext}关闭过程中出现不必要的延迟。
 *
 * 该接口继承了{@link Phased},{@link #getPhase()}方法返回当前的{@link Lifecycle}出于那个阶段（started/stopped）
 * start是最小阶段（{@code Integer.MIN_VALUE}） stop是最高阶段({@code Integer.MAX_VALUE})
 * 启动时(lowest --> highest)  关闭时(highest --> lowest)  相同阶段，则任意启动
 *
 * 任何没有实现{@link SmartLifecycle}的都会被认为phase=0，晚于phase < 0 的组件启动，早于phase > 0 的组件
 *
 * 由于autoStartup的影响，{@link SmartLifecycle}将在应用上下文启动上完成初始化，因此lazy-init对{@link SmartLifecycle}影响很小
 *
 *
 * An extension of the {@link Lifecycle} interface for those objects that require
 * to be started upon {@code ApplicationContext} refresh and/or shutdown in a
 * particular order.
 *
 * <p>The {@link #isAutoStartup()} return value indicates whether this object should
 * be started at the time of a context refresh. The callback-accepting
 * {@link #stop(Runnable)} method is useful for objects that have an asynchronous
 * shutdown process. Any implementation of this interface <i>must</i> invoke the
 * callback's {@code run()} method upon shutdown completion to avoid unnecessary
 * delays in the overall {@code ApplicationContext} shutdown.
 *
 * <p>This interface extends {@link Phased}, and the {@link #getPhase()} method's
 * return value indicates the phase within which this {@code Lifecycle} component
 * should be started and stopped. The startup process begins with the <i>lowest</i>
 * phase value and ends with the <i>highest</i> phase value ({@code Integer.MIN_VALUE}
 * is the lowest possible, and {@code Integer.MAX_VALUE} is the highest possible).
 * The shutdown process will apply the reverse order. Any components with the
 * same value will be arbitrarily ordered within the same phase.
 *
 * <p>Example: if component B depends on component A having already started,
 * then component A should have a lower phase value than component B. During
 * the shutdown process, component B would be stopped before component A.
 *
 * <p>Any explicit "depends-on" relationship will take precedence over the phase
 * order such that the dependent bean always starts after its dependency and
 * always stops before its dependency.
 *
 * <p>Any {@code Lifecycle} components within the context that do not also
 * implement {@code SmartLifecycle} will be treated as if they have a phase
 * value of {@code 0}. This allows a {@code SmartLifecycle} component to start
 * before those {@code Lifecycle} components if the {@code SmartLifecycle}
 * component has a negative phase value, or the {@code SmartLifecycle} component
 * may start after those {@code Lifecycle} components if the {@code SmartLifecycle}
 * component has a positive phase value.
 *
 * <p>Note that, due to the auto-startup support in {@code SmartLifecycle}, a
 * {@code SmartLifecycle} bean instance will usually get initialized on startup
 * of the application context in any case. As a consequence, the bean definition
 * lazy-init flag has very limited actual effect on {@code SmartLifecycle} beans.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.0
 * @see LifecycleProcessor
 * @see ConfigurableApplicationContext
 */
public interface SmartLifecycle extends Lifecycle, Phased {

	/**
	 * The default phase for {@code SmartLifecycle}: {@code Integer.MAX_VALUE}.
	 * <p>This is different from the common phase {@code 0} associated with regular
	 * {@link Lifecycle} implementations, putting the typically auto-started
	 * {@code SmartLifecycle} beans into a later startup phase and an earlier
	 * shutdown phase.
	 * @since 5.1
	 * @see #getPhase()
	 * @see org.springframework.context.support.DefaultLifecycleProcessor#getPhase(Lifecycle)
	 */
	int DEFAULT_PHASE = Integer.MAX_VALUE;


	/**
	 * {@code true}表明{@link Lifecycle}应该在应用上下文刷新时被IoC容器自动启动
	 * {@code false}表明需要显式调用{@link #start()}来启动
	 * Returns {@code true} if this {@code Lifecycle} component should get
	 * started automatically by the container at the time that the containing
	 * {@link ApplicationContext} gets refreshed.
	 * <p>A value of {@code false} indicates that the component is intended to
	 * be started through an explicit {@link #start()} call instead, analogous
	 * to a plain {@link Lifecycle} implementation.
	 * <p>The default implementation returns {@code true}.
	 * @see #start()
	 * @see #getPhase()
	 * @see LifecycleProcessor#onRefresh()
	 * @see ConfigurableApplicationContext#refresh()
	 */
	default boolean isAutoStartup() {
		return true;
	}

	/**
	 * {@link Lifecycle}如果还运行着，那么应该停止它
	 *
	 * 回调函数必须在stop方法执行之后
	 * {@link LifecycleProcessor}仅会调用{@link SmartLifecycle#stop(Runnable)}方法，不会调用{@link Lifecycle#stop()}
	 * 除非{@link SmartLifecycle}的实现中显式地调用了{@link Lifecycle#stop()}
	 *
	 * 默认实现是委派给{@link Lifecycle#stop()}。并且在当前线程中立即出发回调函数执行。
	 * 需要注意的是，这两步操作是没有进行同步的，
	 *
	 * Indicates that a Lifecycle component must stop if it is currently running.
	 * <p>The provided callback is used by the {@link LifecycleProcessor} to support
	 * an ordered, and potentially concurrent, shutdown of all components having a
	 * common shutdown order value. The callback <b>must</b> be executed after
	 * the {@code SmartLifecycle} component does indeed stop.
	 * <p>The {@link LifecycleProcessor} will call <i>only</i> this variant of the
	 * {@code stop} method; i.e. {@link Lifecycle#stop()} will not be called for
	 * {@code SmartLifecycle} implementations unless explicitly delegated to within
	 * the implementation of this method.
	 * <p>The default implementation delegates to {@link #stop()} and immediately
	 * triggers the given callback in the calling thread. Note that there is no
	 * synchronization between the two, so custom implementations may at least
	 * want to put the same steps within their common lifecycle monitor (if any).
	 * @see #stop()
	 * @see #getPhase()
	 */
	default void stop(Runnable callback) {
		stop();
		callback.run();
	}

	/**
	 * 返回{@link Lifecycle}的phase
	 * Return the phase that this lifecycle object is supposed to run in.
	 * <p>The default implementation returns {@link #DEFAULT_PHASE} in order to
	 * let {@code stop()} callbacks execute after regular {@code Lifecycle}
	 * implementations.
	 * @see #isAutoStartup()
	 * @see #start()
	 * @see #stop(Runnable)
	 * @see org.springframework.context.support.DefaultLifecycleProcessor#getPhase(Lifecycle)
	 */
	@Override
	default int getPhase() {
		return DEFAULT_PHASE;
	}

}
