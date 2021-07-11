### BeanFactory循环依赖处理
`AutowiredAnnotationBeanPostProcessor`
- 循环依赖开关(方法)：`AbstractAutowireCapableBeanFactory#setAllowCircularReferences`
- 单例工厂(属性)：`DefaultSingletonBeanRegistry#singletonFactories`
- 获取早期未处理Bean(方法)：`AbstractAutowireCapableBeanFactory#getEarlyBeanReference`
- 早期未处理Bean(属性)：`DefaultSingletonBeanRegistry#earlySingletonObjects`

`singletonFactories` --> `earlySingletonObjects` --> `singletonObjects` 
