## IoC依赖查找
- 依赖查找的前世今生
- 单一类型依赖查找
- 集合类型依赖查找
- 层次性依赖查找
- 延迟性依赖查找
- 安全依赖查找
- 内建可查找的依赖
- 依赖查找中的经典异常

1、依赖查找的前世今生
- 单一类型查找
 - JNDI `javax.naming.Context#lookup(javax.naming.Name)`
 - JavaBeans `java.beans.beancontext.BeanContext`

- 集合类型依赖查找
 - `java.beans.beancontext.BeanContext`

- 层次性依赖查找
 - `java.beans.beancontext.BeanContext`

2、单一类型依赖查找
单一类型依赖查找接口——`BeanFactory`

- 根据Bean名称查找
 - getBean(String)
 - spring 2.5覆盖默认参数： getBean(String, Object...)
- 根据Bean类型查找
 - Bean实时查找
  - Spring 3.0 getBean(Class)
  - Spring 4.1覆盖默认参数：getBean(Class, Object...)
 - Spring 5.1 Bean延迟查找
  - getBeanProvider(Class)
  - getBeanProvider(ResolvableType) 泛型
- 根据Bean名称+类型查找 getBean(String, Class)


3、集合类型依赖查找
集合类型依赖查找接口——`ListableBeanFactory`
- 根据Bean类型查找
 - 获取同类型bean名称列表
  - getBeanNamesForType(Class) 
  - Spring 4.2 getBeanNamesForType(ResolvableType)
 - 获取同类型Bean实例列表
  - getBeansOfType(Class)以及重载方法
- 通过注解类型查找
 - Spring 3.0获取注解类型Bean名称列表
  - getBeanNamesForAnnotation(Class<? extends Annotation>)
 - Spring 3.0 获取注解类型Bean实例列表
  - getBeansWithAnnotation(Class<? extends Annotation>) 
 - Spring 3.0获取指定名称+注解类型bean实例
  - findAnnotationBean(String, Class<? extends Annotation>)

4、层次性依赖查找
层次性依赖查找接口 —— `HierarchicalBeanFactory`
- 双亲BeanFactory `getParentBeanFactory()`
- 层次性查找：
 - 根据Bean名称查找：基于containsLocalBean方法实现
 - 根据Bean类型查找实例列表：
  - 单一类型：BeanFactoryUtils#beanOfType
  - 集合类型：BeanFactoryUtils#beansOfTypeIncludingAncestors
 - 根据Java注解查找名称列表 `BeanFatoryUtils#beanNamesForTypeIncludingAncestors`

5、Bean延迟查找
- ObjectFactory
- ObjectProvider
 - spring5对java8特性扩展
  - getIfAvailable(Supplier)  
  - ifAvailable(Consumer)
  - stream()

6、安全依赖查找
安全定义：获取不存在的bean是否会抛出异常
|依赖查找类型|代表实现|是否安全|  
|---|---|---|  
|单一类型查找|BeanFactory#getBean|否|  
|-|ObjectFactory#getObject|否|
|-|ObjectProvider#getIfAvailable|是|
|集合类型|ListableBeanFactory#getBeansOfType|是|
|-|ObjectProvider#stream|是|

7、内建可查找的依赖
7.1、AbstractApplicationContext内建可查找的依赖
||||
|---|---|---|
|environment|Environment对象|外部化配置以及Profiles|
|systemProperties|java.util.Properties对象|Java系统属性|
|systemEnvironment|java.util.Map对象|操作系统环境变量|
|messageSource|MessageSource对象|国际化文案|
|lifecycleProcessor|LifecycleProcessor|Lifecycle Bean处理器|
|applicationEventMulticaster|ApplicationEventMulticaster对象|spring事件广播对象|

7.2、注解驱动spring应用上下文内建可查找的依赖
||||
|---|---|---|
|org.springframework.context.annotation.internalConfigurationAnnotationProcessor|ConfigurationClassPostProcessor|处理spring配置类|
|org.springframework.context.annotation.internalAutowiredAnnotationProcessor|AutowiredAnnotationBeanPostProcessor|处理@Autowired以及@Value注解|
|org.springframework.context.annotation.internalCommonAnnotationProcessor|CommonAnnotationBeanPostProcessor对象|条件激活，处理JSR-250注解，如@PostConstrcut等|
|org.springframework.context.event.internalEventListenerProcessor|EventListenerMethodProcessor对象|处理注解@EventListener的spring事件监听方法|
|org.springframework.context.event.internalEventListenerFactory|DefaultEventListenerFactory对象|@EventListener事件监听方法适配为ApplicationListener|
|org.springframework.context.annotation.internalPersistenceAnnotationProcessor|PersistenceAnnotationBeanPostProcessor对象|条件激活，处理JPA注解场景|

8、依赖查找经典异常
7.1、BeansException
|异常类型|触发条件|场景|
|---|---|---|
|NoSuchBeanDefinitionException|当查找Bean不存在于IoC容器时|BeanFactory#getBean/ObjectFactory#getObject|
|NoUniqueBeanDefinitionException|类型依赖查找时IoC容器存在多个Bean实例|BeanFactory#getBean|
|BeanInstantiationException|当Bean对应的类型非具体类型时|BeanFactory#getBean|
|BeanCreationException|Bean初始化过程中|Bean初始化方法执行异常时|
|BeanDefinitionStoreException|当BeanDefinition配置元信息非法时|XML配置资源无法打开时|


9、ObjectFactory与BeanFactory的区别
ObjectFactory与BeanFactory均提供依赖查找的能力。
ObjectFactory仅关注一个或者一种类型的Bean依赖查找，并且自身不具备依赖查找能力，能力由BeanFactory输出。
BeanFactory提供了单一类型、集合类型以及层次性等多种依赖查找方式。

10、BeanFactory#getBean是否是线程安全
线程安全，会有synchronized互斥锁控制。