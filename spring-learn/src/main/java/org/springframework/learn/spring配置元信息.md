### Spring配置元信息
- Spring配置元信息
- Spring Bean配置元信息
- Spring Bean属性元信息
- Spring容器配置元信息
- 基于XML文件装载Spring Bean配置元信息
- 基于Properties文件装载Spring Bean配置元信息
- 基于Java注解装载Spring Bean配置元信息
- Spring Bean配置元信息底层实现
- 基于Xml文件装载Spring IoC容器配置元信息
- 基于Java注解装载Spring IoC容器配置元信息 
- 基于Extensible Xml authoring扩展Spring Xml元素
- Extensible Xml authoring扩展原理
- 基于Properties文件装载外部化配置
- 基于Yaml文件装载外部化配置


1、Spring配置元信息
- Spring Bean配置元信息——BeanDefinition
- Spring Bean属性元信息——PropertyValues
- Spring容器配置元信息
- Spring外部化配置元信息——PropertySource：PropertySource @PropertySource
- Spring Profile元信息——@Profile

2、Spring Bean 配置元信息
Bean配置元信息——BeanDefinition
- GenericBeanDefinition : 通用型BeanDefinition
- RootBeanDefinition : 无Parent的BeanDefinition或者合并后的BeanDefinition
- AnnotationBeanDefinition : 注解标注的BeanDefinition

3、Spring Bean属性元信息
3.1、Bean属性元信息——PropertyValues
- 可修改实现——MutablePropertyValues
- 元素成员——PropertyValue

3.2、Bean属性上下文存储——AttributeAccessor
3.3、Bean元信息元素——BeanMetadataElement，可以用于标记BeanDefinition的注册是来自哪里的

4、Spring容器配置元信息
4.1、Spring Xml配置元信息——bean元素相关
`BeanDefinitionParserDelegate`
|beans元素属性|默认值|使用场景|
|---|---|---|
|profile|null(留空)|spring profile配置值|
|default-lazy-init|default|当outter beans "default-lazy-init"属性存在时，继承该值，否则为false|
|default-merge|default|当outter beans "default-merge"属性存在时，继承该值，否则为false|
|default-autowire|default|当outter beans "default-autowire"属性存在时，继承该值，否则为no|
|default-autowire-candidates|null(留空)|默认spring beans名称pattern|
|default-init-method|null(留空)|默认spring beans自定义初始化方法|
|default-destroy-method|null(留空)|默认spring beans自定义销毁方法|

4.2、Spring Xml配置元信息——应用上下文
|Xml元素||
|---|---|
|<context:annotation-config/>|激活spring注解驱动|
|<context:component-scan/>|spring @Component以及自定义注解扫描|
|<context:load-time-weaver/>|激活Spring LoadTimeWeaver|
|<context:mbean-export/>|暴露spring bean作为JMX Beans|
|<context:mbean-server/>|将当前平台作为MBeanServer|
|<context:property-placeholder>|加载外化配置资源作为spring属性配置|
|<context:property-override/>|利用外部化配置资源覆盖spring属性值|

5、基于Xml资源装载Spring Bean配置元信息
5.1、Spring Bean 配置元信息
`XmlBeanDefinitionReader`
|xml元素|使用场景|
|---|---|
|<beans:beans/>|单个xml资源下的多个spring beans配置|
|<bean:bean/>|单个spring bean定义(BeanDefinition)配置|
|<beans:alias/>|为spring bean定义(BeanDefinition)映射别名|
|<beans:import/>|加载外部spring xml配置资源|

6、基于Properties资源装载Spring Bean配置元信息
6.1、Spring Bean配置元信息
`PropertiesBeanDefintionReader`
|Properties属性名|使用场景|
|---|---|
|(class)|Bean类全称限定名|
|(abstract)|是否为抽象的BeanDefinition|
|(parent)|指定parent BeanDefinition名称|
|(lazy-init)|是否为延迟初始化|

7、基于Java注解装载Spring Bean配置元信息
7.1、Spring注解模式注解
|Spring注解|场景说明|版本|
|---|---|---|
|@Repository|数据仓储模式注解|2.0|
|@Component|通用组件模式注解|2.5|
|@Service|服务模式注解|2.5|
|@Controller|Web控制器注解模式|2.5|
|@Configuration|配置类模式注解|3.0|

7.2、Spring Bean依赖注入注解
`ClassPathScanningCandidateComponentProvider`
|Spring注解|场景说明|版本|
|---|---|---|
|@Autowired|Bean依赖注入，支持多种依赖查找方式|2.5|
|@Qualifier|细粒度的@Autowired依赖查找|2.5|

`CommonAnnotationBeanPostProcessor`
|Java注解|场景|版本|
|---|---|---|
|@Resource|类似于@Autowired|2.5|
|@Inject|类似于@Autowired|2.5|

7.3、Spring Bean条件装配注解
|Spring注解|场景说明|版本|
|---|---|---|
|@Profile|配置化条件装配|3.1|
|@Conditional|编程条件装配|4.0|
`ProfileCondition` `ConditionEvaluator`

7.4、Spring Bean生命周期回调注解
|Spring注解|场景说明|版本|
|---|---|---|
|@PostConstructor|替换xml元素<bean init-method="..."/>或者InitializingBean|2.5|
|@PreDestroy|替换xml元素<bean destroy-method="..."/>或者DisposableBean|2.5|
`CommonAnnotationBeanPostProcessor` `InitDestroyAnnotationBeanPostProcessor`

8、Spring Bean配置元信息底层实现
8.1、Spring BeanDefinition解析与注解
|实现场景|实现类|版本|
|---|---|---|
|Xml资源|XmlBeanDefinitionReader|1.0|
|Properties资源|PropertiesBeanDefinitionReader|1.0|
|Java注解|AnnotationBeanDefinitionReader|3.0|
`BeanDefinitionReader` `AbstractBeanDefinitionReader`

8.2、Spring Xml资源BeanDefinition解析与注册
核心Api：`XmlBeanDefinitionReader`
资源：`Resource`
底层：`BeanDefinitionDocumentReader`
Xml解析：Java Dom Level 3 api
BeanDefinition解析：`BeanDefinitionParseDelegate`
BeanDefinition注册：`BeanDefinitionRegistry`

8.3、Spring Properties资源BeanDefinition解析与注册
核心Api：`PropertiesBeanDefinitionReader`
资源：字节流`Resource`、字符流`EncodedResource`
底层：
- 存储：`java.util.Properties`
- BeanDefinition解析：api内部实现
- BeanDefinition注册：BeanDefinitionRegistry

8.4、Spring Java注册BeanDefinition解析与注册
核心Api：`AnnotationBeanDefinitionReader`
资源：类对象`java.lang.Class`
底层：
- 条件评估`ConditionEvaluator`
- Bean范围解析`ScopeMetadataResolver`
- BeanDefinition解析：内部Api实现
- BeanDefinition处理：`AnnotationConfigUtils.processCommonDefinitionAnnotations`
- BeanDefinition注册：`BeanDefinitionRegistry`

9、基于Xml资源装载Spring IoC容器配置元信息
9.1、Spring IoC容器相关Xml配置
xsd文件在本地resources/META-INF/下存在，相关命名空间的处理类在`spring.handlers`文件下注册
|命名空间|所属模块|Schema资源URL|
|---|---|---|
|beans|spring-beans|https://www.springframeworks.org/schema/beans/spring-beans.xsd|
|context|spring-context||
|aop|spring-aop||
|tx|spring-tx||
|util|spring-beans||
|tool|spring-beans|https://www.springframework.org/schema/tool/spring-tool.xsd|

10、基于Java注解装载Spring IoC容器配置元信息
10.1、Spring IoC容器装配注解
|Spring注解|场景说明|版本|
|---|---|---|
|@ImportResource|替换Xml元素<import>|3.0|
|@Import|导入Configuration Class|3.0|
|@ComponentScan|扫描指定package下标注Spring模式注解的类|3.1|

10.2、Spring IoC配置属性注解
|Spring注解|场景说明|版本|
|---|---|---|
|@PropertySource|配置属性抽象PropertySource注解|3.1|
|@PropertySources|@PropertySource集合注解|4.0|

11、基于Extensible Xml Authoring扩展Spring Xml元素
11.1、Spring Xml扩展
- 编写Xml Schema文件：定义xml结构
- 自定义NamespaceHandler实现：命名空间绑定
- 自定义BeanDefinitionParser实现：xml元素与BeanDefinition解析
- 注册xml扩展：命名空间与xml schema映射

12、Extensible Xml authoring扩展原理
12.1、触发时机
`AbstractApplicationContext#obtainFreshBeanFactory` -->
`AbstractApplicationContext#refreshBeanFactory` -->
`AbstractXmlApplicationContext#loadBeanDefinintions` -->
`XmlBeanDefinitionReader#doLoadBeanDefinitions` -->
`BeanDefinitionParserDelegate#parseCustomElement`

12.2、核心流程
`BeanDefinitionParserDelegate#parseCustomElement`
- 获取namespace
- 通过namespace解析NamespaceHandler
- 构造ParserContext
- 解析元素，获取BeanDefinition

13、基于Properties资源装载外部化配置
13.1、注解驱动
- `org.springframework.context.annotation.PropertySource`
- `org.springframework.context.annotation.PropertySources`

13.2、Api编程
- `org.springframework.core.env.PropertySource`
- `org.springframework.core.env.PropertySources`

14、基于Yaml资源装载外部化配置
14.1、Api编程
- `org.springframework.beans.factory.config.YamlProcessor`
- `org.springframework.beans.factory.config.YamlMapFactoryBean`
- `org.springframework.beans.factory.config.YamlPropertiesFactoryBean`

15、Spring配置元信息具体有哪些
- Bean配置元信息：通过媒介(xml/properties等)，解析BeanDefinition
- IoC容器配置元信息：通过媒介(xml/properties等)，控制IoC容器行为，比如注解驱动、aop等
- 外部化配置：通过资源抽象(properties/yaml等)，控制PropertySource
- Spring Profile：通过外部化配置，提供条件分支流程

16、Extensible XML authoring的缺点
- 高复杂度：开发人员需要熟悉Xml Schema，spring.handlers，spring.schemas以及spring api
- 嵌套元素支持较弱：通常需要使用方法递归或者嵌套解析的方式处理嵌套(子)元素
- xml处理性能较差：spring xml基于DOM Level 3 API实现，该api便于理解，然而性能较差
- XML框架移植性差：很难适配高性能和便利性的xml框架，如JAXB 











