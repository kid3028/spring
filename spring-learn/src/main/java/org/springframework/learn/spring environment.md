### Spring Environment
- 理解Spring Environment抽象
- Spring Environment接口使用场景
- Environment占位符处理
- 理解条件装配Spring Profiles
- Spring 4重构@Profile
- 依赖注入Environment
- 依赖查找Environment
- 依赖注入@Value
- Spring类型转换在Environment中的运用
- Spring类型转换在@Value中的运用
- Spring配置属性源PropertySource
- Spring内建的配置属性源
- 基于注解扩展Spring配置属性源
- 基于Api扩展Spring配置属性源
- 课外资料
- 面试题

1、理解spring environment抽象
1.1、统一的spring配置属性管理
spring framework 3.1开始引入environment抽象，它统一spring配置属性的存储，包括占位符处理、类型转换，
不仅完整地替换PropertyPlaceholderConfigurer，而且还支持更加丰富的配置属性源PropertySource

1.2、条件化Spring Bean装配管理
通过Environment Profiles信息，帮助spring容器提供条件化装配bean

2、Spring Environment接口使用场景
- 用于属性占位符处理
- 用于转换spring配置属性类型
- 用于存储spring配置属性源PropertySource
- 用于Profiles状态的维护

3、Environment占位符处理
- spring 3.1前占位符处理
  组件：`org.springframework.beans.factory.config.PropertyPlaceholderConfigurer`   
  接口：`org.springframework.util.StringValueResolver`

- spring 3.1+占位符处理
  组件：`org.springframewok.context.support.PropertySourcesPlaceholderConfigurer`
  接口：`org.springframework.beans.factory.config.EmbeddedValueResolver`

4、理解条件配置Spring Profiles
4.1、Spring 3.1条件配置
Api：`org.springframework.core.env.ConfigurableEnvironment`
- 修改：`addActiveProfile(String)` `setActiveProfiles(String...)` `setDefaultProfiles(String...)`
- 获取：`getActiveProfiles()` `getDefaultProfles()`
- 匹配： `acceptsProfiles(String)` `acceptsProfiles(Profiles)`

注解：`@org.springframework.context.annotation.Profiles`

启动参数激活Profile： `-Dspring.profiles.active=odd`

4.2、基于Spring 4 `org.springframework.context.annotation.Condition`接口实现
`org.springframework.context.annotation.ProfileCondition`

5、依赖注入Environment
5.1、直接依赖注入
- 通过`EnvironmentAware`接口回调
- 通过`@Autowired`注入`Environment`
  关键`ApplicationContextAwareProcessor` `AutowiredAnnotationBeanPostProcessor`

5.2、间接依赖注入(通过applicationContext来获取environment)
- 通过`ApplicationContextAware`接口回调
- 通过`@Autowired`注入`ApplicationContext`

6、依赖查找Environment
6.1、直接依赖查找
通过`org.springframework.context.ConfigurableApplicationContext#ENVIRONMENT_BEAN_NAME`
与依赖注入的environment是同一个对象。关键`ApplicationContextAwareProcessor`
6.2、间接依赖查找
通过`org.springframework.context.ConfurableApplicationContext#getEnvironment`

7、依赖注入`@Value`
通过注入`@Value`: `org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor`

8、Spring类型转换在Environment中的运用
Environment底层实现：
- 底层实现：`org.springframework.core.env.PropertySourcePropertyResolver#convertValueIfNecessary(Object, Class)`
- 底层服务：`org.springframework.core.convert.ConversionService` 
 - 默认实现：`org.springframework.core.convert.support.DefaultConversionService`

9、Spring类型转换在`@Value`中的运用
`@Value`底层实现
- 底层实现：`org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor`
  `org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency`
- 底层服务：`org.springframework.beans.TypeConverter`
 - 底层实现：`org.springframework.beans.TypeConverterDelegate`
   `java.beans.PropertyEditor`
   `org.springframework.core.convert.ConversionService`
   
10、Spring配置属性源PropertySource
10.1、Api
- 单配置属性源：`org.springframework.core.env.PropertySource`
- 多配置属性源：`org.springframework.core.env.PropertySources`

10.2、注解
- 单属性配置源：`org.springframework.context.annotation.PropertySource`
- 多属性配置源：`org.springframework.context.annotation.PropertySources`

10.3、关联
- 存储对象：`org.springframework.core.env.MutablePropertySources`
- 关联方法：`org.springframework.core.env.ConfigurableEnvironment#getPropertySource()`

11、Spring内建的配置属性源
内建PropertySource
|PropertySource类型|说明|
|--|--|
|org.springframework.core.env.CommandLinePropertySource|命令行配置属性源|
|org.springframework.jndi.JndiPropertySource|JNDI配置属性源|
|org.springframework.core.env.PropertiesPropertySource|Properties配置属性源|
|org.springframework.web.context.support.ServletConfigPropertySource|Servlet配置属性源|
|org.springframework.web.context.support.ServletContext.PropertySource|ServletContext配置属性源|
|org.springframework.core.env.SystemEnvironmentPropertySource|环境变量配置属性源|
...等等，关键点：PropertySource的名称，来源


12、基于注解扩展Spring配置属性源
`@org.springframework.context.annotation.PropertySource`实现原理
- 入口：`org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClas`
    `org.springframework.context.annotation.ConfigurationClassParser#processPropertySource`
  
- 4.3新增语义：
 - 配置属性字符编码：encoding
 - `org.springframework.core.io.support.PropertySourceFactory`

- 适配对象：`org.springframework.core.env.CompositePropertySource`

13、基于Api扩张spring配置属性
- Spring应用上下文启动前装载PropertySource
- Spring应用上下文启动后装载PropertySource
默认PropertySource不具备动态更新的能力，因此在Spring上下文启动后修改的数据，能在数据中体现，但是已经注入到bean的数据无法更新到
  
14、课外资料
Spring 4.1测试配置属性源：`@TestPropertySource`

15、简单介绍Spring Environment接口
- 核心接口：`org.springframework.core.env.Environment`
- 父接口：`org.springframework.core.env.PropertyResolver`
- 可配置接口：`org.springframework.core.env.ConfigurableEnvironment`
- 职责：管理Spring配置属性源、管理Profiles
