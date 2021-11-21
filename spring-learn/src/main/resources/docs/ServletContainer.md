### ServletContainerInitializer
该接口的实现将在web application 启动阶段得到通知，执行一些必须的注册操作（servlets/filters/listeners）。
接口的实现类使用 @HandlesTypes 注解来说明onStartup入参`c`需要的类型（实现、继承、注解指定的类注解的类。
如 @HandlesType(A.class)那么所有实现、继承或者被A注解标记的类就会被收集作为onStartup方法的参数）
如果接口实现没有使用 @HandlesTypes 注解，或者注解指定的类没有，那么servlet container将会传递一个null set 给`onStartup`方法。

接口的实现通过java Spi机制（service loader）来加载，需要在jar包的 `/WEB-INF/services/javax.servlet.ServletContainerInitializer`
文件中记录实现类。如spring-web模块中则为`SpringServletContainerInitializer`

#### 核心方法 onStartup
当`ServletContexe`启动时通知`ServletContainerInitializer`的实现
```
// c:接口的实现类使用 @HandlesTypes 注解来说明onStartup入参`c`需要的类型（实现、继承、注解指定的类注解的类。
如 @HandlesType(A.class)那么所有实现、继承或者被A注解标记的类就会被收集作为onStartup方法的参数）
public void onStartup(Set<Class<?>> c, ServletContext ctx) {
    
}
```

### ServletContext
#### addListener
向ServletContext中添加listener，listener需要是下面的类型： 
- ServletContextAttributeListener
- ServletRequestListener
- ServletRequestAttributeListener
- javax.servlet.http.HttpSessionAttributeListener
- javax.servlet.http.HttpSessionIdListener
- javax.servlet.http.HttpSessionListener

如果ServletContext是通过`ServletContainerInitializer#onStartup`传入的，那么添加的listener需要`ServletContextListener`，
其他情况则限定为上面列出的listener类型。

#### addServlet
向`ServletContext`注册Servlet，将会返回一个`ServletRegistration.Dynamic`，通过`ServletRegistration`可以对Servlet进行配置，
如设置loadOnStartup/servletMapping/AsyncSupported

#### addFilter
向`ServletContext`注册Filter，将会返回一个`FilterRegistration.Dynamic`，通过`FilterRegistration`可以对filter进行配置，
如设置AsyncSupport/MappingServletName

### ServletContextListener
接收`ServletContext`生命周期变化的接口。实现类可以通过注解`@WebListener`或者`ServletContext#addListener`注册。
顺序调用`contextInitilizaed`，逆序调用`contextDestroyed`

#### contextInitialized
接收application已经开始初始化的通知。所有的`ServletContextListener`接收到通知时，所有的filters/servlets都还没有初始化。

#### contextDestroyed
接收`ServletContext`将要关闭的通知。在这之前所有的filters/servlets都已经被销毁