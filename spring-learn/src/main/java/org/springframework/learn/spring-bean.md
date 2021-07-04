
 Spring Bean基础  
 
 1、定义Spring Bean  
 2、BeanDefinition元信息  
 3、命名Spring Bean  
 4、Spring Bean别名  
 5、注册Spring Bean  
 6、实例化Spring Bean  
 7、初始化Spring Bean  
 8、延迟初始化Spring Bean  
 9、销毁Spring Bean  
 10、垃圾回收Spring Bean  


 1、定义Spring Bean
  - 什么是BeanDefinition?   
    BeanDefinition是Spring中定义Bean的配置元信息接口，包含：  
      Bean的类名  
      Bean行为配置元素，如作用域、自动绑定的模式、生命周期回调等  
      其他Bean引用，又可以称作合作者(Collaborators) 或者 依赖(Dependencies)  
      配置设置，比如Bean属性(Properties)  
      
 2、BeanDefinition元信息   

| 属性（property） | 说明 |
| :-----| :---- |
| class | bean全类名，必须是具体类，不能用抽象类或接口 |
| name | bean的名称或者id |
| scope | bean的作用域 |
| constructor arguments | bean构造器参数（用于依赖注入） |
| properties | bean属性设置（用于依赖注入） |
| autowiring mode | bean自动绑定模式（byName等） | 
| lazy initialization mode | bean延迟初始化模式 （延迟、非延迟）|
| initialization method | bean初始化回调方法名称 |
| destruction method | bean销毁回调方法 |

2.1、BeanDefinition构建

 - 通过BeanDefinitionBuilder
 - 通过AbstractBeanDefinition以及派生类


3、命名spring bean  
3.1、Bean的名称  
每个Bean拥有一个或者多个标识符(identifiers)，这些标识符在Bean所在的容器必须是唯一的。
通常，一个Bean仅有一个标识符，如果需要额外的，可以考虑使用别名Alias来扩展   
   
在基于XML的配置元信息中，可以用id或者name属性来规定Bean的标识符。通常Bean的标识符由字母组成，允许出现特殊字符。
如果想要引入Bean的别名，可在name舒心中使用逗号或者分号来间隔。   
  
Bean的id或name属性并非必须指定，如果留空的话，容器会为bean自动生成一个唯一的名称。
bean的命名尽管没有限制，不过官方建议采用驼峰的方式，更符合Java的命名约定。   

Bean名称生成器(BeanNameGenerator)  
- DefaultBeanNameGenerator默认通用实现
- AnnotationBeanNameGenerator基于注解扫描的BeanNameGenerator实现   

   
4、Bean的别名   
4.1、Bean别名的价值：   
 - 复用现有的BeanDefinition   
 - 更具有场景化的命名方法，比如： 
```xml
    <!-- datasource 将在子系统A和子系统B中分别叫subsystemA-datasource subsystemB-datasource -->
    <alias name="datasource" alias="subsystemA-datasource"/>
    <alias name="datasource" alias="subsystemB-datasource"/>
```

5、注册Spring Bean
5.1、BeanDefinition注册   
- XML配置元信息
  ```xml
    <bean name="..." class="..."/>
  ```
- Java注解配置元信息
  - @Bean
  - @Component
  - @Import
- Java API 配置元信息
  - 命名方式：`BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)`
  - 非命名方式: `BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition, BeanDefinitionRegistry)`
  - 配置类方式：`AnnotatedBeanDefinitionReader#register(Class...)`
    
6、实例化Spring Bean   
6.1、Bean实例化(Instantiation)  
- 常规方式   
  - 通过构造器(配置元信息：XML、Java注解、Java Api)
  - 通过静态工厂方法(配置元信息：XML、Java Api)
  - 通过Bean工厂方法(配置元信息：XML、Java Api)
  - 通过FactoryBean(配置元信息：XML、Java注解、Java Api)
- 特殊方式
  - 通过ServiceLoaderFactoryBean(配置元信息：XML、Java注解、Java Api)
  - 通过AutowireCapableBeanFactory#createBean(Class, int, boolean)
  - 通过BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)

7、初 始化Spring Bean
7.1、Bean初始化
- @PostConstruct 标注方法
- 实现InitializingBean接口的afterPropertiesSet()方法
- 自定义初始化方法
 - xml配置 `<bean init-method="init">`
 - Java注解 `@Bean(initMehtod="init")`
 - Java Api `AbstractBeanDefinition#setInitMethodName(String)` 三种实现最后都是调用到了 BeanDefinition#setInitMethodName
 - 同时出现以什么顺序执行 `@PostConstruct -> @Bean(InitMethod) -> InitializingBean#afterPropertiesSet`

8、延迟初始化Spring Bean
- Bean延迟初始化(Lazy Initialization)
 - XML配置 `<bean lazy-init="true">`
 - Java注解 `@Lazy(true)`
 - 当某个Bean定义为延迟初始化，spring容器返回的对象与非延迟的对象存在哪些差异？
 - 非延迟初始化在spring应用上下文启动完成后被初始化。

9、销毁Spring Bean
- @PreDestroy
- 实现DisposableBean#destory方法
- 自定义销毁方法
 - `XML配置：<bean destroy="destroy">`
 - `Java注解：@Bean(destroy="destroy"")`
 - Java Api：`AbstractBeanDefinition#setDestroyMethodName(String)`

10、垃圾回收Spring Bean
- 关闭Spring容器（应用上下文）
- 执行GC
- Spring Bean覆盖finalize()方法被回调