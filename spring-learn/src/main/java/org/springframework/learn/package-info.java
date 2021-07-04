package org.springframework.learn;

/*
   轻量级IoC容器
   《Expert One-on-One J2EE Development without EJB》认为轻量级容器应该有的特征
     - A container that can manage application code.
     - A container that is quick to start up.
     _ A container that doesn't require any special deployment steps to deploy objects within it.
     - A container that has such a light footprint and minimal API dependencies that it can be run in a variety of environments.
     - A container that sets the bar for adding a managed object so low in terms of deployment effort and performance overhead
        that it's possible to deploy and manage fine-grained objects, as well as coarse-grained components.

	好处：
	 - Escaping the monolithic container
	 - Maximizing code reusability
	 - Greater object orientation
	 - Greater productivity
	 - Better testability

   依赖查找 vs 依赖注入
   	  类型       依赖处理    实现便利性    代码入侵       API依赖性     可读性
      依赖查找    主动获取    相对繁琐     侵入业务逻辑   依赖容器API    良好
      依赖注入    被动提供    相对便利      低侵入性     不依赖容器api   一般
   spring框架中同时使用了依赖查找和依赖注入

   构造器注入 vs Setter注入
  	 The Spring team generally advocates constructor injection, as it lets you implement application components as immutable objects
  	 and ensures that required dependencies are not null. Furthermore, constructor-injected components are always returned to
  	 the client（calling） code in a fully initialized stat. As a side not, a large number of constructor arguments is a bad code smell,
  	 implying that the class likely has too many responsibilities and should be refactored bo better address proper separation of concerns.

  	 Setter injection should primarily only used for optional dependencies that can be assigned reasonable default values within the class.
  	 Otherwise, not-null checks must be performed everywhere the code uses the dependency. One benefit of setter inject is that setter methods
  	 make objects of that class amenable to reconfiguration or re-injection later.
  	 Management through JMX MBeans is therefore a compelling use case for setter injection.

   Setter注入的缺点
         The order in which setters are called is not expressed in any contract. Thus, we sometimes need to invoke a method after the last setter
     has been called to initialize the component. Spring provides the org.springframework.beans.factory.InitializingBean interface for this;
     it also provides the ability to invoke an arbitrary init method. However, this contract must be documented to ensure correct use outside a container.
         Not all the necessary setter may have been called before use. The object can thus be left partially configured.

   构造器注入的优势：
       Each managed object is guaranteed to be in a consistent state-fully configured, before it be invoked in any business methods. This is the
   primary motivation of Constructor injection. (However, it is possible to achieve the same result with JavaBeans via dependency checking as Spring
   can optionally perform) Theres's no need for initialization methods.
   There may be slightly less code than results from the use of multiple JavaBean methods, although will be no difference in complexity.


	Spring IoC概述
	 - Spring IoC 依赖查找
	 - Spring IoC 依赖注入
	 - Spring IoC 依赖来源
	 - Spring IoC 配元原信息
	 - Spring IoC 容器
	 - Spring 应用上下文
	 - 使用Spring IoC容器
	 - Spring IoC容器生命周期

  Spring IoC 依赖查找
   - 根据Bean名称(id)查找
     - 实时查找
     - 延迟查找
   - 根据Bean类型查找
     - 单个bean对象
     - 集合bean对象
   - 根据bean名称+类型查找
   - 根据Java注解查找
     - 单个bean对象
     - 集合对象

  Spring IoC 依赖注入
    - 根据bean名称注入
    - 根据bean类型注入
      - 单个bean对象
      - 集合bean对象
    - 注入容器内建bean对象
    - 注入非bean对象
    - 注入类型
      - 实时注入
      - 延迟注入

  Spring IoC 依赖来源
  	- 自定义Bean
  	- 容器内建Bean对象
  	- 容器内建依赖

  Spring IoC 配置元信息
    - Bean定义配置
      - 基于XML文件
      - 基于Properties文件
      - 基于Java注解
      - 基于Java API
    - IoC容器配置
      - 基于XML文件
      - 基于Java注解
      - 基于Java API
    - 外部化属性配置
      - 基于Java注解

 - BeanFactory / ApplicationContext 谁才是IoC容器？
    在实现上ApplicationContext 组合了一个 BeanFactory， ApplicationContext所有对bean的操作底层都是基于BeanFactory
    org.springframework.context.support.AbstractRefreshableApplicationContext.beanFactory
    BeanFactory是Spring的底层IoC容器，存储了关于Bean的定义、配置的bean管理对象，
    ApplicationContext组合了BeanFactory，是具备应用特性的BeanFactory的超集

    ApplicationContext除了IoC容器的角色外，还提供如下特性：
      - 面向切面(AOP)
      - 配置元信息(Configuration Metadata)
      - 资源管理(Resource)
      - 事件(Event)
      - 国际化(i18n)
      - 注解(Annotation)
      - Environment(Environment Abstraction)

 - BeanFactory 与 FactoryBean
   - BeanFactory是IoC底层容器
   - FactoryBean是创建bean的一种方式，帮助实现复杂的初始化逻辑

 - Spring IoC容器启动时做了哪些准备
   - IoC配置元信息读取和解析、IoC容器生命周期、Spring事件发布、国际化等
 */