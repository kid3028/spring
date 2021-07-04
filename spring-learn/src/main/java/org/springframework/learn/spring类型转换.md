### Spring类型转换
- Spring类型转换的实现
- 使用场景
- 基于JavaBeans接口的类型转换
- spring内建PropertyEditor扩展
- 自定义PropertyEditor扩展
- Spring PropertyEditor的设计缺陷
- Spring 3通用类型转换接口
- Spring内建类型转换器
- Converter接口的局限性
- GenericConverter接口
- 优化GenericConverter接口
- 扩展Spring类型转换器
- 统一类型转换服务
- ConversionService作为依赖
- 面试题

1、Spring类型转换的实现
1.1、基于JavaBeans接口的类型转换实现
- 基于java.beans.PropertyEditor接口扩展

1.2、Spring 3.0+通用类型转换实现

2、使用场景
|场景|基于JavaBeans接口的类型转换实现|Spring 3.0+通用类型转换实现|
|---|---|---|
|数据绑定|yes|yes|
|BeanWrapper|yes|yes|
|bean属性类型转换|yes|yes|
|外部化属性类型转换|no|yes|

3、基于JavaBeans接口的类型转换
3.1、核心职责
将String类型的内容转换为目标类型的对象

3.2、扩展原理
- Spring框架将文本内容传递到PropertyEditor实现的setText(String)方法
- PropertyEditor#setText(String)方法实现将String类型转换为目标类型的对象
- 将目标类型的对象传入PropertyEditor#setValue(Object)方法
- PropertyEditor#setValue(Object)方法实现需要临时存储传入对象
- Spring框架将通过PropertyEditor#getValue()获取类型转换后的对象


4、Spring内建PropertyEditor扩展
内建扩展(org.springframework.beans.propertyeditors包下)
|转换场景|实现类|
|---|---|
|String --> Byte数组|org.springframework.beans.propertyeditors.ByteArrayPropertyEditor|
|String --> Char|org.springframework.beans.propertyeditors.CharacterEditor|
|String --> Char数组|org.springframework.beans.propertyeditors.CharArrayPropertyEditor|
|String --> Charset|org.springframework.beans.propertyeditors.CharsetEditor|
|String --> Class|org.springframework.beans.propertyeditors.ClassEditor|
|String --> Currency|org.springframework.beans.propertyeditors.CurrencyEditor|

5、自定义PropertyEditor扩展
5.1、扩展模式
扩展`java.beans.PropertyEditorSupport`类

5.2、实现`org.springframework.beans.PropertyEditorRegistrar`
- 实现`rgisterCustomEditors(org.springframework.beans.PropertyEditorRegistry)`方法
- 将`PropertyEditorRegistrar`实现注册为Spring Bean

5.3、向`org.springframework.beans.PropertyEditorRegistry`注册自定义PropertyEditor实现
- 通过类型实现registerCustomEditor(Class<?>, PropertyEditor)
- Java Bean属性类型实现：`registerCustomEditor(Class<?>, String, PropertyEditor)`


6、Spring PropertyEditor设计缺陷
6.1、违反职责单一原则
`java.beans.PropertyEditor`接口职责太多，除了类型转换，还包括Java Beans事件和Java GUI交互

6.2、`java.beans.PropertyEditor`实现类型局限
来源只能为`java.lang.String`

6.3、`java.beans.PropertyEditor`实现缺少类型安全
除了实现类命名可以表达语义，实现类无法感知目标转换类型

7、Spring 3通用类型转换接口
7.1、类型转换接口——`org.springframework.core.convert.converter.Converter<S,T>`
- 泛型化参数S：来源，参数T：目标类型
- 核心方法： `T convert(S)`

7.2、通用类型转换接口——`org.springframework.core.convert.converter.GenericConverter`
- 核心方法：`convert(Object, TypeDescriptor, TypeDescriptor)`
- 配对类型：`org.springframework.core.convert.converter.GenericConverter.ConvertiblePair`
- 类型描述：`org.springframework.core.convert.converter.TypeDescriptor`

8、Spring内建类型转换器
|转换场景|实现类所在包名|
|---|---|
|日期、时间相关|`org.springframework.format.datetime`|
|Java 8日期、时间相关|`org.springframework.format.standard`|
|通用实现|`org.springframework.core.convert.support`|

9、Converter接口的局限性
9.1、缺少SourceType、TargetType前置判断
应对方法：增加`org.springframework.core.convert.converter.ConditionalConveter`实现

9.2、仅能转换单一的SourceType和TargetType
应对方法：使用`org.springframewrok.core.convert.converter.GenericConverter`替代

10、GenericConverter接口
`org.springframework.core.convert.converter.GenericConverter`
|核心要素|说明|
|---|---|
|使用场景|用于复合类型转换场景，比如Collection/Map/数组等，也可以用于单一类型|
|转换范围|`Set<ConvertiblePair> getConvertibleTypes()`|
|配对类型|`org.springframework.core.convert.converter.GenericConverter.ConvertiblePair`|
|转换方法|convert(Object, TypeDescriptor, TypeDescriptor)|
|类型描述|`org.springframework.core.convert.TypeDescriptor`|

11、优化GenericConverter接口
11.1、GenericConverter局限性
- 缺少SourceType和TargetType前置判断
- 单一类型转换实现复杂

11.2、GenericConverter优化接口——ConditionalGenericConverter
- 复合类型转换：`org.springframework.core.convert.converter.GenericConverter`
- 类型条件判断：`org.springframework.core.convert.converter.CondititonalConvertr`

12、扩展Spring类型转换器
12.1、实现转换器接口
- `org.springframework.core.convert.converter.Converter`
- `org.springframework.core.convert.converter.ConverterFactory`
- `org.springframework.core.convert.converter.GenericConverter`

12.2、注册转换器实现
- 通过ConversionServiceFactoryBean 注册为spring bean
- 通过`org.springframework.core.convert.ConversionService` Api

ConversionService设置`org.springframework.beans.AbstractNestablePropertyAccessor.AbstractNestablePropertyAccessor(java.lang.Object, java.lang.String, org.springframework.beans.AbstractNestablePropertyAccessor)`
`org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization`


13、统一类型转换服务
`org.springframwork.core.convert.ConversionService`

|实现类型|说明|
|---|---|
|GenericConversionService|通用ConversionService模板，不内置转换器实现|
|DefaultConversionService|基础ConversionService实现，内置常用转换器实现|
|FormattingConversionService|通用Formatter+GenericConversionService实现，不内置转换器和Formatter实现|
|DefaultFormattingConversionService|DefaultConversionService+格式化实现（JSP-354 Money & Currency, JSR-310 Date-Time）|

14、ConversionService作为依赖
14.1、类型转换器底层接口：`org.springframework.beans.TypeConverter`
- 起始版本：Spring 2.0
- 核心方法：convertIfNecessary重载方法
- 抽象实现：`org.springframework.beans.TypeConverterSupport`
- 简单实现：`org.springframework.beans.SimpleTypeConverter`

14.2、类型转换器底层抽象实现：`org.springframework.beans.TypeConverterSupport`
- 实现接口：`org.springframework.beans.TypeConverter`
- 扩展实现：`org.springframework.beans.PropertyEditorRegistrySupport`
- 委派实现：`org.springframework.beans.TypeConveterDelegate`

14.3、类型转换器底层委派实现：`org.springframework.beans.TypeConverterDelegate`
- 构造来源：`org.springframework.beans.AbstractNestablePropertyAccessor`实现，直接实现`org.springframework.beans.BeanWrapperImpl`
- 依赖：`java.beans.PropertyEditor`实现
 
AbstractApplicationContext --> "conversionService" ConversionService bean --> BeanFactory --> AbstractBeanFactory#getConversionService -->
BeanDefinition --> BeanWrapper --> 属性转换(数据来源：PropertyValues) --> setPropertyValues(PropertyValues) 
--> TypeConverter#convertIfNecessary --> TypeConverterDelegate#convetIfNessary --> PropertyEditor or ConversionService
    
15、Spring类型转换实现有哪些？
- 基于JavaBeans PropertyEditor接口实现
- Spring 3.0+通用类型转换实现

16、Spring类型转换器接口
- 类型转换接口：`org.springframework.core.convert.converter.Converter`
- 通用类型转换接口：`org.springframework.core.convert.converter.GenericConverter`
- 类型条件接口：`org.springframework.core.convert.converter.ConditionalConverter`
- 综合类型转换接口：`org.springframework.core.convert.converter.ConditionalGenericConverter`
- 底层实现：`org.springframework.beans.TypeConverter`











    





