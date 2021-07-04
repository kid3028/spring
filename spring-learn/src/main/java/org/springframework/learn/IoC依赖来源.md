### Spring IoC依赖来源
- 依赖查找的来源
- 依赖注入的来源
- Spring容器管理和游离对象
- Spring BeanDefinition作为依赖来源
- 单例对象作为依赖来源
- 非Spring容器管理对象作为依赖来源
- 外部化配置作为依赖来源

1、依赖查找的来源
1.1、业务定义
|来源|配置元数据|
|---|---|
|Spring BeanDefinition|`<bean id="user" class="...">`|
|-|`@Bean public User user() {...}`|
|-|`BeanDefinitionBuilder`|
|单例对象|API实现|

1.2、Spring内建BeanDefinition
内建可查找的依赖
AbstractApplicationContext内建可查找的依赖
||||
|---|---|---|
|environment|Environment对象|外部化配置以及Profiles|
|systemProperties|java.util.Properties对象|Java系统属性|
|systemEnvironment|java.util.Map对象|操作系统环境变量|
|messageSource|MessageSource对象|国际化文案|
|lifecycleProcessor|LifecycleProcessor|Lifecycle Bean处理器|
|applicationEventMulticaster|ApplicationEventMulticaster对象|spring事件广播对象|

注解驱动spring应用上下文内建可查找的依赖
||||
|---|---|---|
|org.springframework.context.annotation.internalConfigurationAnnotationProcessor|ConfigurationClassPostProcessor|处理spring配置类|
|org.springframework.context.annotation.internalAutowiredAnnotationProcessor|AutowiredAnnotationBeanPostProcessor|处理@Autowired以及@Value注解|
|org.springframework.context.annotation.internalCommonAnnotationProcessor|CommonAnnotationBeanPostProcessor对象|条件激活，处理JSR-250注解，如@PostConstrcut等|
|org.springframework.context.event.internalEventListenerProcessor|EventListenerMethodProcessor对象|处理注解@EventListener的spring事件监听方法|
|org.springframework.context.event.internalEventListenerFactory|DefaultEventListenerFactory对象|@EventListener事件监听方法适配为ApplicationListener|
|org.springframework.context.annotation.internalPersistenceAnnotationProcessor|PersistenceAnnotationBeanPostProcessor对象|条件激活，处理JPA注解场景|


2、依赖注入来源
2.1、注入来源
|来源|配置元数据|
|---|---|
|Spring BeanDefinition|`<bean id="user" class="...">`|
|-|`@Bean public User user{...}` |
|-|BeanDefinitionBuilder|
|单例对象|Api实现|
|非Spring容器管理对象(ResolvableDependency游离对象)|BeanFactory/ApplicationContext/ResourceLoader/ApplicationEventMulticaster 可以通过@Autowired注入，但是无法通过getBean获取|

3、Spring容器管理和游离对象
3.1、依赖对象
|来源|Spring Bean对象|生命周期管理|配置元信息|使用场景|
|---|---|---|---|---|
|Spring BeanDefinition|是|是|有|依赖查找、依赖注入|
|单体对象|是|否|无|依赖查找、依赖注入|
|Resolvable Dependency|否|否|无|依赖注入|

4、Spring BeanDefinition作为依赖来源
- 元数据：BeanDefinition
- 注册：BeanDefinitionRegistry#registerBeanDefinition
- 类型：延迟、非延迟
- 顺序：Bean生命周期顺序按照注册顺序 

5、单例对象作为依赖来源
5.1、要点
- 来源：外部普通Java对象，不一定是POJO
- 注册：SingletonBeanRegistry#registrySingleton

5.2、限制
- 无生命周期管理
- 无法实现延迟初始化Bean

6、非Spring容器管理对象作为依赖来源
- 注册：`ConfigurableListableBeanFactory#registerResolvableDependency`
- 限制：
 - 无生命周期管理
 - 无法实现延迟初始化Bean
 - 无法通过依赖查找

7、外部化配置作为依赖来源(@Value)
- 类型：非常规Spring对象依赖来源
  解析：EmbeddedValueResolvers
- 限制：
 - 无生命周期管理
 - 无法实现延迟初始化Bean
 - 无法通过依赖查找

8、单例对象能在IoC容器启动后注册吗？
可以。到哪里对象的注册与BeanDefinition不同，BeanDefinition会被`ConfigurableListableBeanFactory#freezeConfiguration()`方法影响，
从而冻结注册，单例对象则没有这个限制。可以随时调用`DefaultSingletonBeanRegistry#registerSingleton`方法注册单例对象