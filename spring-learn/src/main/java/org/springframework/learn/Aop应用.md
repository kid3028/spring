### Spring Aop在Spring事件(Event)的应用
- 核心Api`EventPublicationInterceptor`
- 特性描述：当Spring AOP代理Bean中的JoinPoint方法执行后，Spring ApplicationContext将发布一个自定义事件(ApplicationEvent子类)
- 使用限制：EventPublicationInterceptor关联的ApplicationEvent子类必须存在单参数的构造器(Object)
          EventPublicationInterceptor需要被声明为Spring Bean


### Spring Aop在Spring事务(Transactions)理论基础
#### 核心Api
- Spring事务`@Enable`模块驱动：`@EnableTransactionManagement`
- Spring事务注解：`@Transactional`
- Spring事务事件监听器：`@TransactionalEventListener`
- Spring事务定义：`TransactionDefinition`
- Spring事务状态：`TransactionStatus`
- Spring平台事务管理器：`PlatformTransactionManager`
- Spring事务代理配置：`ProxyTransactionManagementConfiguration`
- Spring事务PointcutAdvisor实现：`BeanFactoryTransactionAttributeSourceAdvisor`
- Spring事务MethodInterceptor实现：`TransactionInterceptor`
- Spring事务属性源：`TransactionAttributeSource`

#### TransactionDefinition
> Interface that defines Spring-compliant Transaction properties. Based on the propagation behavior analogous to EJB 
> CMT attribute.

核心方法：
- `Isolation#getIsolationLevel()`：获取隔离级别，默认值`ISOLATION_DEFAULT`
- `Propagation#getPropagationBehavior()`：获取事务传播，默认值`PROPAGATION_REQUIRED`
- `getTimeout()`：获取事务执行超时时间，默认值`TIMEOUT_DEFAULT`
- `isReadOnly()`：是否为只读事务，默认false

#### TransactionStatus
> Interface that specifies an API to programmatically manage transaction savePoints in a generic fashion. 
> Extended by TransactionStatus ti expose savePoints management functionality for a specific transaction.

核心方法：
- `isNewTransaction()`：当前事务执行是否在新的事务
- `setRollbackOnly()`：将当前事务设置为只读
- `isRollbackOnly()`：当前事务是否为只读
- `isCompleted()`：当前事务是否完成

#### 平台管理器PlatformTransactionManager
> The central interface in Spring's transaction infrastructure. Applications can use this directly, but it is
> not primarily meant as API: Typically, applications will work with either TransactionTemplate or declarative
> transaction through AOP

核心方法：
- `getTransaction(TransactionDefinition)`：获取事务状态
- `commit(TransctionStatus)`：提交事务
- `rollback(TransactionStatus)`：回滚事务

#### 事务传播TransactionPropagation
主要：`PROPAGATION_REQUIRED`、`PROPAGATION_REQUIRES_NEW`、`PROPAGATION_NESTED`


### Spring Aop在Spring事务(Transactions)源码



### Spring Aop在Spring缓存(Caching)
#### 核心Api
- spring缓存@Enable模块驱动：`@EnableCaching`
- 缓存操作注解：`@Caching`/`@Cachable`/`@CachePut`/`@CacheEvict`
- 缓存配置注解：`@CacheConfig`
- 缓存注解操作数据源：`AnnotationCacheOperationSource`
- Spring缓存注解解析器：`SpringCacheAnnotationParser`
- Spring缓存管理器：`CahceManager`
- Spring缓存接口：`Cache`
- Spring缓存代理配置：`ProxyCachingConfiguration`
- Spring缓存PointcutAdvisor实现：`BeanFactoryCacheOperationSourceAdvisor`
- Spring缓存MethodInterceptor实现：`CacheInterceptor`

实现与事务类似
通过Enable模块驱动，判断是哪种代理(Proxy/AspectJ)
Source中包含多个Parser，parser解析注解信息，以ReturningAdvice操作

### Spring Aop在Spring本地调度(Scheduling)
#### 核心Api
- Spring异步@Enable模块驱动：`@EnableAsync`
- Spring异步注解：`@Async`
- Spring异步配置器：`AsyncConfigurer`
- Spring异步代理配置：`ProxyAsyncConfiguration`
- Spring异步PointcutAdvisor：`AsyncAnnotationAdvisor`
- Spring异步MethodInterceptor实现：`AnnotationAsyncExceptionInterceptor`