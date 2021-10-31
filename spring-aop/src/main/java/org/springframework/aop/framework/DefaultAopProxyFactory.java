/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.aop.framework;

import org.springframework.aop.SpringProxy;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * 默认的 {@link AopProxyFactory} 实现，基于CGLIB 或者 JDK动态代理
 * 如果
 *  optimize = true
 *  proxyTargetCLass = true
 *  len(interfaces) = 0
 * 其中一个满足将会使用CGLIB创建代理
 *
 * Default {@link AopProxyFactory} implementation, creating either a CGLIB proxy
 * or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true for a given
 * {@link AdvisedSupport} instance:
 * <ul>
 * <li>the {@code optimize} flag is set
 * <li>the {@code proxyTargetClass} flag is set
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>In general, specify {@code proxyTargetClass} to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 12.03.2004
 * @see AdvisedSupport#setOptimize
 * @see AdvisedSupport#setProxyTargetClass
 * @see AdvisedSupport#setInterfaces
 */
@SuppressWarnings("serial")
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

	/**
	 * optimize：用来控制通过cglib创建的代理是否使用激进的优化策略。除非完全了解aop代理如果处理优化，否则不推荐用户使用这个设置。
	 *          目前这个属性仅用于cglib代理，对于jdk动态代理无效
	 * proxyTargetClass: 这个属性为true时，目标类本省被代理而不是目标目标类的接口。如果这个属性设置为true，cglib将被创建，设置方式 <aop:aspectj-autoproxy  proxy-target-class="ture"/>
	 * hasNoUserSuppliedProxyInterfaces：是否存在代理接口
	 *
	 * 代理方式：
	 *   如果目标对象实现了接口，默认情况下会采用jdk的动态代理实现aop
	 *   如果目标对象实现了接口，可以强制使用cglib实现aop
	 *   如果目标对象没有实现接口，必须采用cglib
	 *
	 * 强制使用cglib：
	 *    添加cglib库
	 *    spring配置文件中配置  <aop:aspectj-autoproxy proxy-target-class="true"/>
	 *
	 * jdk动态代理与cglib字节码生成的区别：
	 *    jdk动态代理只能对实现了接口的类生成代理，而不能针对类
	 *    cglib是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法，因为是继承，所以该类或方法最好不要声明成final
	 *
	 * public interface UserService {
	 *     void add();
	 * }
	 *
	 * public class UserServiceImpl implements UserService {
	 *     public void add() {
	 *         System.out.println("-------add-------");
	 *     }
	 * }
	 *
	 * public class MyInvocationHandler implements InvocationHandler {
	 *     private Object target;
	 *
	 *     public MyInvocationHandler(Object target) {
	 *         super();
	 *         this.target = target;
	 *     }
	 *
	 *     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	 *         System.out.println("----------before---------")
	 *
	 *         Object result := method.invoke(target, args);
	 *
	 *         System.out.println("----------after---------")
	 *
	 *         result.return
	 *     }
	 *
	 *     public Object getProxy() {
	 *         return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this)
	 *     }
	 * }
	 *
	 * public class ProxyTest {
	 *     public void testProxy() throw Throwable {
	 *         UserService userService = new UserServiceImpl();
	 *         MyInvocationHandler invocationHandler = new MyInvocationHandler(userService);
	 *         UserService proxy = (UserService) invocationHandler.getProxy();
	 * 		   proxy.add();
	 *     }
	 * }
	 *
	 *
	 * cglib是一个强大的高性能代码生成包，被广泛的aop框架使用，例如spring aop、 dynaop，为他们提供方法的拦截。
	 * hibernate也使用cglib来实现 多对一、一对一， EasyMock/ jMock通过mock对象完成测试，
	 * 他们都是通过使用cglib来为没有接口的类创建mock对象
	 *
	 * cglib底层使用一个小而快的字节码处理框架 ASM，来转换字节码并生成新的类。
	 *
	 * public class EnhanceDemo {
	 *     public static void main(String[] args) {
	 *         Enhancer enhancer = new Enhancer();
	 *         enhancer.setSuperclass(EnhancerDemo.class);
	 *         enhancer.setCallback(new MethodInterceptorImpl());
	 *
	 *         EnhancerDemo demo = (EnhancerDemo) enhancer.create();
	 *         demo.test();
	 *         System.out.println(demo);
	 *     }
	 *
	 *     public void test() {
	 *         System.out.println("EnhanceDemo test()");
	 *     }
	 *
	 *     private static class MethodInterfaceImpl Implements MethodInterceptor {
			 * @override
			 * public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throw Throwable {
			 *     System.out.println("------------before invoke-----------")
			 *     Object result = proxy.invokeSuper(obj, args)
			 *     System.out.println(---------------after invoke---------------")
			 *     return result;
			 * }
	 *     }
	 * }
	 *
	 * @param config the AOP configuration in the form of an
	 * AdvisedSupport object
	 * @return
	 * @throws AopConfigException
	 */
	@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			return new JdkDynamicAopProxy(config);
		}
	}

	/**
	 * Determine whether the supplied {@link AdvisedSupport} has only the
	 * {@link org.springframework.aop.SpringProxy} interface specified
	 * (or no proxy interfaces specified at all).
	 */
	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
		Class<?>[] ifcs = config.getProxiedInterfaces();
		return (ifcs.length == 0 || (ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0])));
	}

}
