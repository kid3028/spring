### Spring应用上下文生命周期
- Spring应用上下文启动准备阶段
- BeanFactory创建阶段
- BeanFactory准备阶段
- BeanFactory后置处理阶段
- BeanFactory注册BeanPostProcessor阶段
- 初始化内建Bean: MessageSource
- 初始化内建Bean: Spring事件广播器
- Spring应用上下刷新阶段
- Spring事件监听器注册阶段
- BeanFactory初始化完成阶段
- Spring应用上下文启动完成阶段
- Spring应用上下文启动阶段
- Spring应用上下停止阶段
- Spring应用上下关闭阶段
- 面试题

1、spring应用上下文启动准备阶段
`AbstractApplicationContext#prepareRefresh()`
- 启动事件startupDate
- 状态标识closed(false)、active(true)
- 初始化PropertySource——`initPropertySources()`
- 检测Environment中必有的属性
- 初始化事件监听器集合
- 初始化早期spring事件集合

2、BeanFactory创建阶段
`AbstractApplicationContext#obtainFreshBeanFactory()`方法
- 刷新spring应用上下文底层BeanFactory-`refreshBeanFactory()`
 - 销毁或关闭BeanFactory，如果已存在的话
 - 创建BeanFactory——`createBeanFactory()`
 - 设置BeanFactoryId
 - 设置"是否允许BeanDefinition重复定义"——`customizeBeanFactory(DefaultListableBeanFactory)`
 - 设置"是否允许循环引用(依赖)"——`customizedBeanFactory(DefaultListableeBeanFactory)`
 - 加载BeanDefinition——`loadBeanDefinition(DefaultListableBeanFactory)`方法
 - 关联新建BeanFactory到spring应用上下文

- 返回Spring应用上下文底层BeanFactory——`getBeanFactory()`

3、BeanFactory准备阶段
`AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)`
- 关联Classloader：xml中只是填写了class的全限定名，需要有一个classloader将class加载；扩展出类隔离机制，特殊需求的扩展点
- 设置Bean表达式处理器
- 添加PropertyEditorRegistrar实现——`ResourceEditorRegistrar`
- 添加Aware回调接口BeanPostProcessor实现——`ApplicationContextAwareProcessor`
- 忽略Aware回调接口作为依赖注入接口
- 注册ResolvableDependency对象——`BeanFactory` `ResourceLoader` `ApplicationEventPublisher` `ApplicationContext`
- 注册`ApplicationListenerDetector`对象
- 注册`LoadTimeWeaverAwareProcessor`对象
- 注册单例对象——`Environment` `Java System Properties`以及OS环境变量

4、BeanFactory后置处理阶段
`AbstractApplicationContext#postProcessBeanFactory(ConfigurableListableBeanFactory)`由子类覆盖该方法

`AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)`
- 调用`BeanFactoryPostProcessor`或者`BeanDefinitionRegistry`后置处理方法
- 注册`LoadTimeWeaverAwareProcessor`对象

5、BeanFactory注册BeanPostProcessor
`AbstractApplicationContext#registerBeanPostProcessors(ConfigurableListableBeanFactory)`
- 注册`PriorityOrdered`类型的BeanPostProcessor Beans
- 注册`Ordered`类型的BeanPostProcessor Beans
- 注册普通BeanPostProcessor Beans
- 注册MergedBeanDefinitionPostProcessor Beans
- 注册ApplicationListenerDetector对象

6、初始化内建Bean：MessageSource
`AbstractApplicationContext#initMessageSource()`

7、初始化内建Bean：Spring事件广播器
`AbstractApplicationContext#initApplicationEventMulticaster()`

8、Spring应用上下文刷新阶段
`AbstractApplicationContext#onRefresh()`
非web环境下没有实现，web环境下大多实现也都为国际化ui，子类覆盖该方法
- `org.springframework.web.context.support.AbstractRefreshableWebApplication#onRefresh()`
- `org.springframework.web.context.support.GenericWebApplicationContext#onRefresh()`
- `org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext#onRefresh()`
- `org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext#onRefresh()`
- `org.springframework.web.context.support.StaticWebApplicationContext#onRefresh()`

9、Spring事件监听器注册阶段
`AbstractApplicationContext#registeListeners()`
- 添加当前应用上下文所关联的ApplicationListener对象集合
- 添加BeanFactory所注册的ApplicationListener Beans
- 广播早期Spring事件

10、BeanFactory初始化完成阶段
`AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)`
- BeanFactory关联ConversionService Bean，如果存在
- 添加StringValueResolver对象(占位符处理)
- 依赖查找LoadTimeWeaverAware Bean(aop)
- BeanFactory临时ClassLoader置为null(主要用在aop，整个BeanFactory中存在主classloader、bean classloader、临时classloader)
- BeanFactory冻结配置(freezeConfiguration())
- BeanFactory初始化非延迟单例Beans


11、Spring应用上下文刷新完成阶段
`AbstractApplicationContext#finishRefresh()`
- 清除ResourceLoader缓存——`clearResourceCaches` @since 5.0
- 初始化LifecycleProcessor对象 + `initLifecycleProcessor()`
- 调用`LifecycleProcessor#onRefresh()`方法
- 发布Spring应用上下文已刷新事件——`ContextRefreshedEvent`
- 向MBeanServer托管Live Beans(JMX中的MBean管理器托管存活或者活跃的Spring上下文Bean)


12、Spring应用上下文启动阶段
`AbstractApplicationContext#start()`
- 启动LifecycleProcessor
 - 依赖查找Lifecycle Beans
 - 启动Lifecycle Beans
- 发布spring应用上下文启动事件——ContextStartedEvent

13、Spring应用上下文停止阶段
`AbstractApplicationContext#stop()`
- 停止LifecycleProcessor
 - 依赖查找Lifecycle Beans
 - 停止Lifecycle Beans
- 发布Spring应用上下文已停止事件——ContextStoppedEvent

14、Spring应用上下文关闭阶段
`AbstractApplicationContext#close()`
- 状态标识: active(false) 、 closed(true)
- Live Beans JMX撤销：`LiveBeansView.unregisterApplicationContext(ConfigurableApplicationContext)`
- 发布spring应用上下文关闭事件——`ContextClosedEvent`
- 关闭LifecycleProcessor
 - 依赖查找Lifecycle Beans
 - 停止Lifecycle Beans
- 销毁Spring Beans
- 关闭BeanFactory
- 回调onClose()
- 注册Shutdown Hook线程(如果曾经注册过)

14、Environment完整生命周期
































