### Spring事件
- Java事件、监听器编程模型
- 面向接口的事件、监听器设计模式
- 面向注解的事件、监听器设计模式
- spring标准事件ApplicationEvent
- 基于接口的Spring事件监听器
- 基于注解的Spring事件监听器
- 注册Spring ApplicationListener
- Spring事件发布器
- Spring层次性上下文事件传播
- Spring内建事件
- Spring 4.2 Payload事件
- 自定义Spring事件
- 依赖注入ApplicationEventPublisher
- 依赖查找ApplicationEventMulticaster
- ApplicationEventPublisher底层实现
- 同步和异步Spring事件广播
- Spring 4.1事件异常处理
- Spring 事件、监听器实现原理
- 课外资料
- 面试题

1、Java事件、监听器编程模式
1.1、设计模式——观察者模式扩展
- 可观察对象(消息发送者)：`java.util.Observable`
- 观察者——`java.util.Observer`

1.2、标准化接口
- 事件对象——`java.util.EventObject`
- 事件监听器——`java.util.EventListener`

2、面向接口的事件/监听器设计模式
事件、监听器场景举例
|Java技术规范|事件接口|监听器接口|
|---|---|---|
|JavaBeans|`java.beans.PropertyChangeEvent`|`java.beans.PropertyChangeListener`|
|Java AWT|`java.awt.event.MouseEvent`|`java.awt.event.MouseListener`|
|Java Swing|`javax.swing.event.MenuEvent`|`javax.swing.event.MenuListener`|
|Java Preference|`java.util.prefs.PreferenceChangeEvent`|`java.util.prefs.PreferenceChangeListener`|

3、面向注解的事件、监听器设计模式
事件、监听器注解场景举例
|Java技术规范|事件注解|监听器注解|
|---|---|---|
|Servlet 3.0+||@javax.servlet.annotation.WebListener|
|JPA 1.0+|@javax.persistence.PostPersist||
|Java Common|@PostConstruct||
|EJB 3.0+|@javax.ejb.PrePassivate|| 
|JSF 2.0+|@javax.faces.event.ListenerFor||

4、Spring标准事件——ApplicationEvent
4.1、Java标准事件 `java.util.EventObject`扩展
扩展特性：事件发生事件戳(时间戳)

4.2、Spring应用上下文ApplicationEvent扩展`ApplicationContextEvent`
- Spring应用上下文(ApplicationContext)作为事件源
- 具体实现
 - `org.springframework.context.event.ContextClosedEvent`
 - `org.springframework.context.event.ContextRefreshedEvent`
 - `org.springframework.context.event.ContextStartedEvent`
 - `org.springframework.context.event.ContextStoppedEvent`


5、基于接口的Spring事件监听器
5.1、Java标准事件监听器`java.util.EventListener`扩展
- 扩展接口`org.springframework.context.ApplicationListener`
- 设计特点：单一类型事件处理
- 处理方法：`onApplicationEvent(ApplicationEvent)`
- 事件类型：`org.Springframework.context.ApplicationEvent`

6、基于注解的Spring事件监听器
Spring注解——`@org.springframework.context.event.EventListener`
|特性|说明|
|---|---|
|设计特点|支持多ApplicationEvent类型，无需接口约束|
|注解目标|方法|
|是否支持异步执行|支持|
|是否支持泛型类型事件|支持|
|是否支持顺序控制|支持，@Order|


7、注册Spring ApplicationListener
方法1：ApplicationListener作为Spring Bean注册
方法2：通过ConfigurableApplicationContext Api注册
`ctx.addApplicationListener(new ApplicationListener)`

8、spring事件发布器
方法一：通过ApplicationEventPublisher（依赖注入）发布spring事件
方法二：通过ApplicationEventMulticaster（依赖注入、依赖查找）发布Spring事件

9、Spring层次性上下文事件传播
9.1、发生说明
当spring应用出现多层次及spring应用上下文（ApplicationContext）时，如Spring Mvc、Spring Boot或者Spring Cloud场景下，
由子ApplicationContext发起Spring事件可能会传递大其parent ApplicationContext(直到Root)的过程

9.2、如何避免
定位spring事件源(ApplicationContext)进行过滤处理

10、Spring内建事件
ApplicationContextEvent派生事件
- ContextRefreshedEvent：spring应用上下文就绪事件
- ContextStartedEvent：spring应用上下文启动事件
- ContextStoppedEvent：spring应用上下文停止事件
- ContextClosedEvent：spring应用上下文关闭事件

11、Spring 4.2 Payload事件
spring Payload事件——`org.springframework.context.PayloadApplicationEvent`
- 使用场景：简化spring事件发送，关注事件源主体
- 发送方法：`ApplicationEventPublisher#publishEvent(Object)`
payload是泛型化的，但是泛型实现不彻底，存在问题，日常直接使用`ApplicationEventPublisher#publishEvent(Object)`发布事件即可，没必要将其包装为payload

``` java
 // err
 MyPayload extends PayloadApplicationEvent<String> 
 
 MyPayload<String> extends PayloadApplicationEvent<String> 
```
12、自定义Spring事件
- 扩展`org.springframework.context.ApplicationEvent`
- 实现`org.springframework.context.ApplicationListener`
- 注册`org.springframework.context.ApplicationListener`

13、依赖注入ApplicationEventPublisher
- 通过ApplicationEventPublisherAware回调接口
- 通过@Autowired ApplicationEventPublisher

14、依赖查找ApplicationEventMulticaster
- bean名称指定：`applicationEeventMulticaster`
- bean类型指定：`org.springframework.context.event.ApplicationEventMulticaster`
`org.springframework.context.support.AbstractApplicationContext.initApplicationEventMulticaster`
  
15、ApplicationEventPublisher底层实现
- 接口：`org.springframework.context.event.ApplicationEventMulticaster`
- 抽象类：`org.springframework.context.event.AbstractApplicationEventMulticaster`
- 实现类：`org.springframework.context.event.SimpleApplicationEventMulticaster`

``` java
AbstractApplicationContext --> ApplicationContext --> ApplicationEventPublisher
AbstractApplicationContext#applicationEventMulticater = SimpleApplicationEventMulticaster
```

16、同步和异步Spring事件广播
16.1、基于实现类：`org.springframework.context.event.SimpleApplicationMulticaster`
- 模式切换：`setTaskExecutor(java.util.concurrent.Executor)方法`
 - 默认模式：同步
 - 异步模式：`java.util.concurrent.ThreadPoolExcutor`
- 设计缺陷： 非基于接口契约编程

16.2、基于注解`@org.springframework.context.event.EventListener`
- 模式切换
 - 默认模式：同步
 - 异步模式：标注`@org.springframework.scheduling.annotation.Async`
- 实现限制：无法直接使用同步、异步动态切换

17、Spring 4.1事件异常处理
spring3.0错误处理接口`org.springframework.util.ErrorHandler`
- 使用场景
 - Spring事件(Events):`SimpleApplicationEventMulticaster` spring4.1开始支持
 - Spring本地调度(Scheduling)
  - `org.springframework.scheduling.concurrent.ConcurrentTaskScheduler`
  - `org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler`

18、Spring事件、监听器实现原理
核心类：`org.springframework.context.event.SimpleApplicationEventMulticaster`
- 设计模式：观察者模式扩展
 - 被观察者：`org.springframework.context.ApplicationListener`
  - Api添加
  - 依赖查找
 - 通过对象：`org.springframework.context.ApplicationEvent`
- 执行模式： 同步、异步
- 异常处理：`org.springframework.util.ErrorHandler`
- 泛型处理：`org.springframework.core.ResolvableType`

19、课外资料
19.1、SpringBoot事件
|事件类型|发生时机|
|---|---|
|ApplicationStartingEvent|springBoot应用已启动|
|ApplicationStartedEvent|SpringBoot应用已启动|
|ApplicationEnvironmentPreparedEvent|SpringBoot Environment实例已准备时|
|ApplicationPreparedEvent|SpringBoot应用预备时|
|ApplicationReadyEvent|SpringBoot应用完全可用时|
|ApplicationFailedEvent|SpringBoot应用启动失败时|

19.2、SpringCloud事件
|事件类型|发生时机|
|---|---|
|EnvironmentChangeEvent|当Environment示例配置属性发生变化时|
|HeartbeatEvent|当DiscoveryClient客户端发送心跳时|
|InstancePreRegisteredEvent|当服务实例注册前|
|InstanceRegisteredEvent|当服务实例注册后|
|RefreshEvent|当RefreshEndpoint被调用时|
|RefreshScopeRefreshedEvent|当RefreshScopeBean刷新后|

20、Spring事件核心接口、组件
- Spring事件ApplicationEvent
- Spring事件监听器ApplicationListener
- Spring事件发布器ApplicationEventPublisher
- Spring事件广播器ApplicationEventMulticaster

21、Spring同步、异步事件处理的使用场景
- spring同步事件——绝大多数spring使用场景，如ContextRefreshedEvent
- Spring异步事件——主要@EventListener与@Async配合，实现异步处理，不阻塞线程，比如长时间的数据计算任务等。
不要轻易调整SimpleApplicationEventMulticaster中关联的taskExecutor对象，除非使用者非常了解Spring事件机制，否则容易出现异常行为。