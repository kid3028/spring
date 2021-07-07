### Spring应用上下文生命周期
- Spring应用上下文启动准备阶段
- BeanFactory创建阶段
- BeanFactory准备阶段
- BeanFactory后置处理阶段
- BeanFactory注册BeanPostProcessor阶段
- 初始化内建Bean: MessageSource
- 初始化内建Bean: Spring事件广播器
- Spring应用上下刷新阶段
- Spring事件监听器注册阶段
- BeanFactory初始化完成阶段
- Spring应用上下文启动完成阶段
- Spring应用上下文启动阶段
- Spring应用上下停止阶段
- Spring应用上下关闭阶段
- 面试题

1、spring应用上下文启动准备阶段
`AbstractApplicationContext#prepareRefresh()`
- 启动事件startupDate
- 状态标识closed(false)、active(true)
- 初始化PropertySource——`initPropertySources()`
- 检测Environment中必有的属性
- 初始化事件监听器集合
- 初始化早期spring事件集合

2、BeanFactory创建阶段
`AbstractApplicationContext#obtainFreshBeanFactory()`方法
- 刷新spring应用上下文底层BeanFactory-`refreshBeanFactory()`
 - 销毁或关闭BeanFactory，如果已存在的话
 - 创建BeanFactory——`createBeanFactory()`
 - 设置BeanFactoryId
 - 设置"是否允许BeanDefinition重复定义"——`customizeBeanFactory(DefaultListableBeanFactory)`
 - 设置"是否允许循环引用(依赖)"——`customizedBeanFactory(DefaultListableeBeanFactory)`
 - 加载BeanDefinition——`loadBeanDefinition(DefaultListableBeanFactory)`方法
 - 关联新建BeanFactory到spring应用上下文

- 返回Spring应用上下文底层BeanFactory——`getBeanFactory()`

3、BeanFactory准备阶段
`AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)`
- 关联Classloader：xml中只是填写了class的全限定名，需要有一个classloader将class加载；扩展出类隔离机制，特殊需求的扩展点
- 设置Bean表达式处理器
- 添加PropertyEditorRegistrar实现——`ResourceEditorRegistrar`
- 添加Aware回调接口BeanPostProcessor实现——`ApplicationContextAwareProcessor`
- 忽略Aware回调接口作为依赖注入接口
- 注册ResolvableDependency对象——`BeanFactory` `ResourceLoader` `ApplicationEventPublisher` `ApplicationContext`
- 注册`ApplicationListenerDetector`对象
- 注册`LoadTimeWeaverAwareProcessor`对象
- 注册单例对象——`Environment` `Java System Properties`以及OS环境变量

4、BeanFactory后置处理阶段
`AbstractApplicationContext#postProcessBeanFactory(ConfigurableListableBeanFactory)`由子类覆盖该方法

`AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)`
- 调用`BeanFactoryPostProcessor`或者`BeanDefinitionRegistry`后置处理方法
- 注册`LoadTimeWeaverAwareProcessor`对象
























