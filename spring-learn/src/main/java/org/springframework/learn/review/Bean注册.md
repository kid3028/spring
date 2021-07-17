### BeanDefinition接口
BeanDefinition是对bean描述信息的抽象，提供了bean属性、构造器等信息的描述。
- setParentName(String parentName)/get: 设置父bean名称
- setBeanClassName(String beanClassName)/get: bean的class可以在 BeanFactoryPostProcessor中进行替换，例如替换为变体实现
- setScope(String scope)/get: 覆盖bean作用域
- setLazyInit(boolean lazyInit)/get: 是否延迟初始化。false则在beanFactory启动时便进行实例化
- setDependsOn(String... dependsOn)/get: 当前bean依赖的其他bean，BeanFactory将保证依赖的bean优先初始化
- setAutowireCandidate(boolean autowireCandidate)/get: 标记当前是否其他bean依赖注入的候选。仅用于类型自动装配
- setPrimary(boolean primary)/get
- setFactoryBeanName(String factoryBeanName)/get: 指定工厂Bean名称
- setFactoryMethodName(String factoryMethodName)/get: 该方法将被factoryBean以有参或无参构造器实例调用；如果factoryBean没有指定，则调用当前bean的同名静态方法。

- `ConstructorArgumentValues getConstructorArgumentValues()`:当前Bean的构造器参数，不会为null。返回的实例对象可以在BeanFactoryPostProcessor中进行修改
- `boolean hasConstructorArgumentValues()`

- `MutablePropertyValues getPropertyValues()`: 当前Bean属性，可以在BeanFactoryPostProcessor中被修改
- `boolean hasPropertyValues()`

- setInitMethodName(String initMethodName)/get: bean的初始化方法
- setDestroyMethodName(String destroyMethodName)/get: bean的销毁方法

### 相关类
```
BeanDefinition
- AbstractBeanDefinition
-- GenericBeanDefintion
-- RootBeanDefinition
-- ChildBeanDefinition

- AnnotatedBeanDefinition
-- AnnotatedGenericBeanDefinition
-- ScannedGenericBeanDefinition

- BeanDefinitionBuilder
```
