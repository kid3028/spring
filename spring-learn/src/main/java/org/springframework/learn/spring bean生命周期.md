Spring Bean生命周期
- Spring Bean元信息配置阶段
- Spring Bean元信息解析阶段
- Spring Bean注册阶段
- Spring BeanDefinition合并阶段
- Spring Bean Class加载阶段
- Spring Bean实例化前阶段
- Spring Bean实例化阶段
- Spring Bean实例化后阶段
- Spring Bean属性赋值前阶段
- Spring Bean Aware接口回调阶段
- Spring Bean初始化前阶段
- Spring Bean初始化阶段
- Spring Bean初始化后阶段
- Spring Bean初始化完成阶段
- Spring Bean销毁前阶段
- Spring Bean销毁阶段
- Spring Bean垃圾收集


1、Spring Bean元信息配置阶段
1.1、BeanDefinition配置
- 面向资源
 - XML配置
 - Properties配置
- 面向注解`@Configuration`  `@Component`  `@Bean`
- 面向Api `BeanDefinitionBuilder`

2、Spring Bean元信息解析阶段
2.1、面向资源BeanDefinition解析
- BeanDefinitionReader
- XML解析器-BeanDefinitionParser

2.2、面向注解BeanDefinition解析
- AnnotatedBeanDefinitionReader

3、Spring Bean注册阶段
BeanDefinition注册接口：`BeanDefinitionRegistry`
`DefaultListableBeanDefinitionRegistry#beanDefinitionMap`
`DefaultListableBeanDefinitionRegistry#beanDefinitionNames`

4、BeanDefinition合并
4.1、父子BeanDefinition合并
- 当前BeanFactory查找
- 层次BeanFactory查找
`org.springframework.beans.factory.config.ConfigurableBeanFactory.getMergedBeanDefinition`
  
5、Spring Bean Class加载阶段
- ClassLoader类加载
- Java Security安全控制
- ConfigurableBeanFactory临时ClassLoader

6、Spring Bean实例化前阶段
6.1、非主流生命周期——Bean实例化前阶段
`InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation`返回非null，则终止该bean的实例化行为，使用返回对象作为实例化对象

7、Spring Bean实例化阶段
7.1、实例化方式
- 传统实例化方式
 - 实例化策略：`InstantiationStrategy`
- 构造器依赖注入

8、Spring Bean实例化后阶段
8.1、Bean属性赋值(populate)判断
`InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation`返回true则继续填充bean属性，返回false则终止bean属性的填充

9、Spring Bean属性赋值前阶段
9.1、Bean属性元信息
- `PropertyValues`
9.2、Bean属性赋值前回调
- Spring 1.2-5.0：`InstantiationAwareBeanPostProcessor#postProcessPropertyValues`
- Spring 5.1：`InstantiationAwareBeanPostProcessor#postProcessProperties`

10、Spring Bean Aware接口回调阶段
Aware接口回调也归属于bean初始化，回调发生在初始化中`org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)`
10.1、Spring Aware接口(按照如下的顺序顺序执行)
`org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeAwareMethods`
- BeanNameAware
- BeanClassLoaderAware
- BeanFactoryAware
 
下面的接口依赖ApplicationContext，在DefaultListableBeanFactory下无法回调 
`org.springframework.context.support.ApplicationContextAwareProcessor.postProcessBeforeInitialization`
`org.springframework.beans.factory.config.ConfigurableBeanFactory.addBeanPostProcessor`中添加了ApplicationContextAwareProcessor

- EnvironmentAware
- EmbeddedValueResolverAware
- ResourceLoaderAware
- ApplicationEventPublisherAware
- MessageSourceAware
- ApplicationContextAware

11、Spring Bean初始化前阶段
11.1、已完成
- Bean实例化
- Bean属性赋值
- Bean Aware接口回调

11.2、方法回调
- `BeanPostProcessor#postProcessBeforeInitialization`

12、Spring Bean初始化阶段
12.1、Bean初始化(Initialization)
- @PostConstructor
- 实现InitializingBean接口的afterPropertiesSet()
- 自定义初始化方法

13、Spring Bean初始化后阶段
13.1、方法回调
- `BeanPostProcessor#postProcessAfterInitialization`

14、Spring Bean初始化完成阶段
14.1、方法回调
- Spring 4.1+：`SmartInitializingSigleton#afterSingletonsInstantiated`

15、Spring Bean销毁前阶段
只是在spring容器中被销毁，被不代表在java环境中被销毁
15.1、方法回调
`DestructionAwareBeanPostProcessor#postProcessBeforeDestruction`
`BeanFactory#destroyBean(bean)`

16、Spring Bean销毁阶段
16.1、Bean销毁(Destroy)
- @PreDestroy
- 实现DisposableBean#destroy
- 自定义销毁方法

17、Spring Bean垃圾收集
17.1、Bean垃圾回收(GC)
- 关闭spring容器（应用上下文）
- 执行GC
- Spring Bean覆盖finalize()方法


18、BeanPostProcessor的使用场景有哪些？
BeanPostProcessor提供了Spring Bean初始化前、初始化后的生命周期回调，分别对应postProcessBeforeInitialization/postProcessAfterInitialization方法，
允许对关心的bean进行扩展，甚至是替换。
其中ApplicationContext相关的Aware回调也基于BeanPostProcessor实现，即ApplicationContextAwareProcessor。

19、BeanFactoryPostProcessor与BeanPostProcessor的区别？
BeanFactoryPostProcessor是Spring BeanFactory(ConfigurableListableBeanFactory)的后置处理器，用于扩展BeanFactory，或者通过BeanFactory进行依赖查找和依赖注入。
BeanFactoryPostProcessor必须有Spring ApplicationContext执行，BeanFactory无法直接交互。
BeanPostProcessor则直接与BeanFactory关联，属于N对1关系


20、BeanFactory是怎样处理Bean生命周期的？
BeanFactory的默认实现为DefaultListableBeanFactory，其中Bean生命周期与方法映射如下：
- BeanDefinition注册阶段——registerBeanDefinition
- BeanDefinition合并阶段——getMergedBeanDefinition
- Bean实例化前阶段——resolveBeforeInstantiation
- Bean实例化节点——createBeanInstance
- Bean实例化后阶段——populateBean
- Bean属性赋值前阶段——populateBean
- Bean属性赋值阶段——populateBean
- Bean Aware接口回调阶段——initializeBean
- Bean初始化前阶段——initializeBean
- Bean初始化阶段——initializeBean
- Bean初始化后阶段——initializeBean
- Bean初始化完成阶段——preInstantiatedSingletons
- Bean销毁前阶段——destroyBean
- Bean销毁阶段——destroyBean











