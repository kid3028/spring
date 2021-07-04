### Spring资源管理
- 引入动机
- Java标准资源接口
- Spring内建Resource实现
- Spring Resource接口扩展
- Spring 资源加载器
- Spring通配路径资源加载器
- Spring通配路径资源扩展
- 依赖注入Spring Resource
- 依赖注入ResourceLoader
- 面试精选

1、引入动机
1.1、为什么Spring不使用Java标准资源管理，而选择重新发明轮子？
- Java标准资源管理强大，然而扩展复杂，资源存储方式并不统一
- Spring要自立门户
- Spring“抄”、“超”、“潮”

2、Java标准资源管理
|指责|说明|
|---|---|
|面向资源|文件系统、artifact(jar/war/ear文件)以及远程资源(Http/FTP等)|
|Api整合|`java.lang.ClassLoader#getResource` `java.io.File` `java.net.URL`|
|资源定位|`java.net.URL` `java.net.URI`|
|面向流式存储|`java.net.URLConnection`|
|协议扩展|`java.net.URLStreamHandler` `java.net.URLStreamHandlerFactory`|

2.1、基于`java.net.URLStreamHandler`扩展协议
JDK 1.8内建协议实现
|协议|实现类|
|---|---|
|file|sun.net.www.protocol.file.Handler|
|ftp|sun.net.www.protocol.file.Handler|
|http|sun.net.www.protocol.http.Handler|
|https|sun.net.www.protocol.https.Handler|
|jar|sun.net.www.protocol.jar.Handler|
|mailto|sun.net.www.protocol.mailto.Handler|
|netdoc|sun.net.www.protocol.netdoc.Handler|

2.2、基于`java.net.URLStreamHandler`扩展协议
实现类名必须为"Handler"
|实现类名规则|说明|
|---|---|
|默认|sun.net.www.protocol.${protocol}.Handler|
|自定义|通过Java Properties java.protocol.handler.pkgs指定实现类包名，实现类名必须为Handler，如果存在多包名，通过分隔符"|"|

3、Spring资源接口
|类型|接口|
|---|---|
|输入流|org.springframework.core.io.InputStreamSource|
|只读资源|org.springframework.core.io.Resource|
|可写资源|org.springframework.core.io.WritableResource|
|编码资源|org.springframework.core.io.support.EncodedResource|
|上下文资源|org.springframework.core.io.ContextResource|

4、Spring内建Resource实现
|资源来源|资源协议|实现类|
|---|---|---|
|Bean定义|-|org.springframework.beans.factory.support.BeanDefinitionResource（很少用）|
|数组|-|org.springframework.core.io.ByteArrayResource|
|类路径|classpath:/|org.springframework.core.io.ClassPathResource|
|文件系统|file:/|org.springframework.core.io.FileSystemResource|
|URL|URL支持的协议|org.springframework.core.io.UrlResource|
|ServletContext|无|org.springframework.web.context.support.ServletContextResource|

5、Spring Resource接口扩展
5.1、可写资源接口
`org.springframework.core.io.WritableReource`
- `org.springframework.core.io.FileSystemResource`
- `org.springframework.core.io.FileUrlResource`
- `org.springframework.core.io.PathResource`

5.2、编码资源接口
`org.springframework.core.io.support.EncodedResource`

6、Spring资源加载器
- Resource加载器
 - org.springframework.core.io.ResourceLoader
  - org.springframework.core.io.DefaultResourceLoader
   - org.springframework.core.io.FileSystemResourceLoader
   - org.springframework.core.io.ClassRelativeResourceLoader
   - org.springframework.context.support.AbstractApplicationContext

7、Spring通配路径资源加载器
7.1、通配路径ResourceLoader
- org.springframework.core.io.support.ResourcePatternResolver
 - org.springframework.core.io.support.PathMatchingResourcePatternResolver

7.2、路径匹配器
- org.springframework.util.PathMatcher
 - Ant模式匹配实现：org.springframework.util.AntPathMatcher
- 重置PathMatcher：PathMatchingResourcePatternResolver#setPathMatcher

8、依赖注入Spring Resource
8.1、基于@Value实现
``` java
@Value("classpath:/...")
private Resource resource;

@Value("classpath*:/...")
private Resource[] resources;
```

9、依赖注入ResourceLoader
- 方法一：实现ResourceLoaderAware回调
- 方法二：@Autowired注入ResourceLoader
- 方法三：注入ApplicationContext作为ResourceLoader

10、spring配置资源中有哪些常见的类型
- xml资源
- properties资源
- yaml资源

11、举例不同类型的spring配置资源
- xml资源
 - 普通BeanDefinition xml配置资源：*.xml
 - spring schema资源：*.xsd

- Properties资源
 - 普通Properties格式资源：*.properties
 - Spring Handler实现类映射文件： META-INF/spring.handlers
 - Spring Schema资源映射文件： MATA-INF/spring.schemas

- Yaml资源
 - 普通yaml配置资源： *.yaml *.yml

12、Java标准资源扩展的步骤
- 简易实现：
  实现URLStreamHandler并放置在sun.net.www.protocol.${protocol}.Handler包下

- 自定义实现
 - 实现URLStreamHandler
 - -Djava.protocol.handler.pkgs启动参数，指向URLStreamHandler实现类的包下

- 高级实现
 - 实现URLStreamHandlerFactory并传递到URL中

