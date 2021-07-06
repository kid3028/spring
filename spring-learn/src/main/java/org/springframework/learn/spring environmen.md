


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