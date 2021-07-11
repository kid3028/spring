### 依赖查找or注入的Bean会被缓存吗？
1、单例Bean(Singleton)会缓存
缓存位置：`org.springframework.beans.factory.support.DefaultSingletonBeanRegistry#singletonObjects`属性

2、原型Bean(Prototype)不会缓存
当依赖查询或者依赖注入时，根据BeanDefinition每次创建

3、其他Scope Bean
- request：每个ServletRequest内部缓存，生命周期维持在每次Http请求
- session：每个HttpSession内部缓存，生命周期位置在每个用户Http会话
- application：当前Servlet应用内部缓存