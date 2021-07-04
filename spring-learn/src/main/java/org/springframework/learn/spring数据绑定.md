### Spring数据绑定
- Spring数据绑定使用场景
- Spring数据绑定组件
- Spring数据绑定元数据局
- Spring数据绑定控制参数
- Spring底层Java Beans替换实现
- BeanWrapper的使用场景
- 课外资料
- DataBinder数据校验
- 面试题

1、Spring数据绑定使用场景
- Spring BeanDefinition到Bean实例创建
- Spring数据绑定(DataBinder)
- Spring Web参数绑定(WebDataBinder)

2、Spring数据绑定组件
2.1、标准组件
- org.springframework.validation.DataBinder
将PropertyValues绑定到某个对象上

2.2、Web组件
- org.springframework.web.bind.WebDataBinder
- org.springframework.web.bind.ServletRequestDataBinder
- org.springframework.web.bind.support.WebRequestDataBinder
- org.springframework.web.bind.support.WebExchangeDataBinder

2.3、DataBinder核心属性
|属性|说明|
|---|---|
|target|关联目标Bean|
|objectName|目标Bean名称|
|bindingResult|属性绑定结果|
|typeConverter|类型转换器|
|conversionService|类型转换服务|
|messageCodesResolver|校验错误文案Code处理器|
|validators|关联的Bean Validator实例集合|

2.4、DataBinder绑定方法
`bind(PropertyValues)`将PropertyValues Key-Value内容映射关联到Bean(target)中的属性上。
假设PropertyValues中包含“name=qull”的键值对，同时bean对象User中存在name属性，当bind方法执行时，User对象中name属性值将被绑定为“qull”

3、Spring数据绑定元数据
3.1、DataBinder元数据——PropertyValues
|特征|说明|
|数据来源|BeanDefinition，主要来源于xml资源配置BeanDefinition|
|数据结构|由一个或者多个PropertyValue组成|
|成员结构|PropertyValue包含属性名称，以及属性值(包括原始值、类型转换后的值)|
|常见实现|MutablePropertyValues|
|Web扩展实现|ServletConfigPropertyValues、ServletRequestParameterPropertyValues|
|相关生命周期|InstantiationAwareBeanPostProcessor#postProcessProperties|

4、Spring数据绑定控制参数
4.1、DataBinder绑定特殊场景分析
- 当PropertyValues中包含名称x的PropertyValue，目标对象B不存在x属性，当bind方法执行时会发生什么？
- 当PropertyValues中包含名称x的PropertyValue，目标对象B中存在x属性，当bind方法执行时，如何避免B属性x不被绑定？
- 当PropertyValues中包含名称x.y的PropertyValue，目标对象B中存在x属性(嵌套y属性)，当bind方法执行时，会发生什么？

4.2、DataBinder绑定控制参数
|参数名称|说明|
|---|---|
|ignoreUnknownFields|是否忽略未知字段，默认true|
|ignoreInvalidFields|是否忽略非法字段，默认false|
|autoGrowNestedPaths|是否自动增加嵌套路径，默认true|
|allowedFields|绑定字段白名单|
|disallowedFields|绑定字段黑名单|
|requiredFields|必须绑定字段|


5、Spring底层Java Beans替换实现
5.1、JavaBeans核心实现——`java.beans.BeanInfo`
- 属性（Property）：`java.beans.PropertyEditor`
- 方法(Method)
- 事件(Event)
- 表达式(Expression)

5.2、Spring替代实现——`org.sprigframework.beans.BeanWrapper`
- 属性(Property)——`java.beans.PropertyEditor`
- 嵌套属性路径(nested path)

6、BeanWrapper的使用场景
- Spring底层JavaBeans基础设施的中心化接口
- 通常不会直接使用，间接用于BeanFactory和DataBinder
- 提供标准JavaBeans分析和操作，能单独或批量存储Java Bean的属性(properties)
- 支持嵌套属性路径(nested path)
- 实现类`org.springframework.beans.BeanWrapperImpl`

7、课外资料
|Api|说明|
|---|---|
|java.beans.Introspector|Java Beans内省api|
|java.beans.BeanInfo|Java Bean元信息api|
|java.beans.BeanDescriptor|Java Bean信息描述符|
|java.beans.PropertyDescriptor|Java Bean属性描述符|
|java.beans.MethodDescriptor|Java Bean方法描述符|
|java.beans.EventSetDescriptor|Java Bean事件集合描述符|

8、DataBinder数据校验
8.1、DataBinder与BeanWrapper
- bind方法生成BeanPropertyBindingResult
- BeanPropertyBindingResult关联BeanWrapper

9、Spring数据绑定的Api是什么？
`org.springframwork.validation.DataBinder`
关联的类有`BeanWrapper` `BandingResult`