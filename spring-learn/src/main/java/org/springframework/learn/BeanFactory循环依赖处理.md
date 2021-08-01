### BeanFactory循环依赖处理
`AutowiredAnnotationBeanPostProcessor`
- 循环依赖开关(方法)：`AbstractAutowireCapableBeanFactory#setAllowCircularReferences`
- 单例工厂(属性)：`DefaultSingletonBeanRegistry#singletonFactories`
- 获取早期未处理Bean(方法)：`AbstractAutowireCapableBeanFactory#getEarlyBeanReference`
- 早期未处理Bean(属性)：`DefaultSingletonBeanRegistry#earlySingletonObjects`

`singletonFactories` --> `earlySingletonObjects` --> `singletonObjects` 

- getSingleton -- singletonsCurrentlyInCreation判断是否已经创建 or 是否在创建中
- 如果没有创建，并且是单例、父container中没有，将beanName放入alreadyCreated中，表示即将创建
- 再次尝试从singletonObjects中获取，并传递工厂对象ObjectFactory
- beanName同步加入singletonsCurrentlyInCreation
- 调用ObjectFactory#getObject,lambda (() -> createBean(beanName, mbd, args)) --> doCreateBean(beanName, mbdToUse, args)
- 使用BeanWrapper包装bean创建过程 --> 工厂创建 or 选择构造器创建
- 进入循环依赖处理
- 允许暴露早期bean
- 将bean封装成ObjectFactory，加入singletonFactories
``` java
				this.singletonFactories.put(beanName, singletonFactory);
				this.earlySingletonObjects.remove(beanName);
				this.registeredSingletons.add(beanName);
```
- 执行属性填充populateBean(beanName, mbd, instanceWrapper)
 - 使用BeanPostProcessor完成
 - ApplicationContextAwareProcessor
 - ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor
 - PostProcessorRegistrationDelegate$BeanPostProcessorChecker
 - CommonAnnotationBeanPostProcessor
 - AutowiredAnnotationBeanPostProcessor
 - ApplicationListenerDetector
