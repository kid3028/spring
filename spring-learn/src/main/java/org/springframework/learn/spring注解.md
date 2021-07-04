### Spring注解
- Spring注解驱动编程发展历程
- Spring核心注解场景分类
- Spring注解编程模型
- Spring元注解(Meta-Annotations)
- Spring模式注解(Stereotype Annotations)
- Spring组合注解(Composed Annotations)
- Spring注解属性别名(Attribute Aliases)
- Spring注解属性覆盖(Attribute Overrides)
- Spring@Enable模块驱动
- Spring条件注解
- 课外资料
- 面试题

1、Spring注解驱动编程发展历程
- 注解驱动启蒙事件：Spring1.0
  1.2`@Transactional`只支持本地事务，不支持分布式事务，不能跨线程
  1.2`@ManagedResource`JMX(java management extension)补充
- 注解驱动过渡时代：Spring2.0
  2.0`@Repository`
  2.5`@Component`
  2.5`@Service`
  2.5`@Controller`
- 注解驱动黄金时代：Spring3.0
  `@Bean`
  `@Lazy`
  `@Primary`
  `@Configuration`
  `@ImportResourcce`
  3.1`@ComponentScan`
- 注解驱动完善时代：Spring4.0
  `@Condition`
  `@Conditional`
- 注解驱动当下时代：Spring5.0
  `@Indexed`性能优化，通过APT(Annotation Processor Tools)在编译时生成元信息，以减少类的扫描，加快启动
  
2、Spring核心注解场景分类
2.1、Spring模式注解
|Spring注解|场景说明|版本|
|---|---|---|
|@Repository|数据仓储模式注解|2.0|
|@Component|通用组件模式注解|2.5|
|@Service|服务模式注解|2.5|
|@Controller||web控制器2.5|
|@Configuration|配置类|3.0|

2.2、装配注解
|spring注解|场景说明|版本|
|---|---|---|
|@ImportResource|替换xml元素<import>|2.5|
|@Import|导入Configuration|2.5|
|@ComponentScan|扫描指定package下标注spring模式注解的类|3.1|

2.3、依赖注入注解
|Spring注解|场景说明|版本|
|---|---|---|
|@Autowired|Bean依赖注入，支持多种依赖查找方式|2.5|
|@Qualifier|细粒度的@Autowired依赖查找|2.5|

3、Spring注解编程模型
- 元注解Meta-Annotation
- Spring模式注解Stereotype Annotation
- Spring组合注解Composed Annotation
- Spring注解属性别名和覆盖Attribute Aliases and Overrides
`https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model`
  
3.1、Spring元注解(Meta-Annotation)
A meta-annotation is an annotation that is declared on another annotation. An annotation is therefore meta-annotated if it is
annotated with another annotation. For example, any annotation that is declared to be documented is meta-annotated with @Documented
`@Documented` `@Inherited` `@Repeatable`


3.2、Spring模式注解Stereotype Annotation
标记为某个角色

@Component派生性：元注解@Component的注解在xml元素<context:component-scan>或者注解@ComponentScan扫描中，派生了@Component的特性，
并且从spring4.0开始支持多层次派生性
`@Repository` `@Service` `@Controller` `@Configuration` `@SpringBootConfiguration`

3.2.1、@Component派生性原理
- 核心组件：`ClassPathBeanDefinitionScanner` `ClassPathScanningCandidateComponentProvider`
- 资源处理：`ResourcePatternResolver`
- 资源——类元信息：`MetadataReaderFactory`
- 类元信息：`ClassMetadata`
 - ASM实现：`ClassMetadataReadingVistor`
 - 反射实现：`StandardAnnotationMetadata`
- 注解元信息：`AnnotationMetadata`
 - ASM实现：`AnnotationMetadataReadingVistor`
 - 反射实现：`StandardAnnotationMetadata`

3.3、Spring组合注解Composed Annotations
Spring组合注解Composed Annotations中的元注解允许是Spring模式注解Stereotype Annotation与其他Spring功能性注解的任意组合
`@SpringBootApplication`

4、Spring注解属性别名Attribute Alias
显式别名 、隐式别名、传递别名
```
@ComponentScan中的显式别名
-------------
@AliasFor("basePackages")
String[] value() defualt {};

@AliasFor("value")
String[] basepackages() default {};
```

```
@SpringBootApplication中的隐式
----------------------
// SpringBootApplication#excluedName等价于EnableAutoConfiguration#excludedName
@AliasFor(annotation = EnableAutoConfiguration.class)
String[] excluedName() default {};

@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
String[] scanBasePackages() default {}；
```

5、Spring注解覆盖Attribute Overrides
看官方文档，和Attribute Alias非常相似
`https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model`

6、Spring @Enable模块驱动
@Enable模块驱动是以@Enable为前缀的注解驱动编程模型。所谓"模块"是指具有相同领域的功能组件集合，组合所形成一个独立的单元。
比如Web Mvc模块、AspectJ代理模块、Caching模块、JMX模块、Async模块。
`@EnableWebMvc`
`@EnableTransationManagement`
`@EnableCaching`
`@EnableMBeanExport`
`@EnableAsync`

6.1、@Enable模块驱动编程模式
- 驱动注解：`@EnableXXX`
- 导入注解：`@Import`具体实现
- 具体实现：
 - 基于Configuration Class
 - 基于ImportSelector接口
 - 基于ImportBeanDefinitionRegistrar接口实现

7、Spring条件注解
7.1、基于配置条件注解`@Profile`
- 关联对象：`org.springframework.core.env.Environment`中Profiles
- 实现变化：从spring4.0开始，@Profile基于@Conditional实现

7.2、基于编程条件注解`@Conditional`
- 关联对象`org.springframework.context.annotation.Condition`具体实现

7.3、`@Conditional`实现原理
- 上下文对象：`org.springframework.context.annotation.ConditionContext`
- 条件判断：`org.springframework.context.annotation.ConditionEvaluator`
- 配置阶段：`org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase`
- 判断入口：`org.springframework.context.annoation.ConfigurationClassPostProcessor`
`org.springframework.context.annotation.ConfigurationClassParser`
  
8、SpringBoot注解
|注解|场景说明|版本|
|---|---|---|
|@SpringBootConfiguration|SpringBoot配置类|1.4|
|@SpringBootApplication|SpringBoot应用引导注解|1.2|
|@EnableAutoConfiguration|SpringBoot激活自动装配|1.0|

9、SpringCloud注解
|注解|场景说明|版本|
|---|---|---|
|@SpringCloudApplication|SpringCloud应用引导注解|1.0|
|@EnableDiscoveryClient|SpringCloud激活服务发现客户端注解|1.0|
|@EnableCircuitBreaker|SpringCloud激活熔断注解|1.0|

10、Spring的模式注解有哪些
`@Component` `@Repository` `@Service` `@Controller` `@Configuration`

11、@EventListener工作原理
`org.springframework.context.event.EventListenerMethodProcessor`
