### Spring 校验
设计比较失败、难用
- Spring校验使用的场景
- Validator接口设计
- Errors接口设计
- Errors文案来源
- 自定义Validator
- Validator的救赎
- 面试题

1、Spring校验使用场景
- Spring常规检验(Validator)
- Spring数据绑定(DataBinder)
- Spring Web参数绑定(WebDataBinder)
- Spring WebMvc、WebFlux处理方法参数校验

2、Validator接口设计
2.1、接口职责
Spring内部校验器接口，通过编程的方式校验目标对象

2.2、核心方法
- supports(Class)：校验目标类能否校验
- validate(Object, Errors)：校验目标对象，并将校验失败的内容输出到Errors对象

2.3、配套组件
- 错误收集器：org.springframework.validation.Errors
- Validator工具类：org.springframework.validator.ValidationUtils

3、Errors接口设计
3.1、接口职责
数据绑定和校验错误收集接口，与Java Bean和其属性有强关联性

3.2、核心方法
- reject方法(重载)：收集错误文案
- rejectValue方法(重载)：收集对象字段中的错误文案

3.3、配套组件
- Java Bean错误描述：org.springframework.validation.ObjectError
- Java Bean属性错误描述：org.springframework.validation.FieldError

4、Errors文案来源
Errors文案生成步骤
- 选择Errors实现(如:org.springframework.validation.BeanPropertyBindingResult)
- 调用reject、rejectValue方法
- 获取Errors对象中ObjectError或者FieldError
- 将ObjectError或者FieldError中的code和args，关联MessageSource实现(如ResourceBoundMessageSource)

5、自定义Validator
实现org.springframework.validation.Validator接口
- 实现supports接口
- 实现validate方法
 - 通过Errors对象收集错误
  - ObjectError：对象Bean错误
  - FieldError：对象Bean属性Property错误
 - 通过ObjectError、FieldError关联MessageSource实现获取最终文案

6、Validator的救赎
Bean validator与Validator适配
- 核心组件：org.springframework.validation.beanvalidation.LocalValidatorFactoryFactoryBean
- 依赖Bean Validator：JSR-303  JSR-349 provider
- Bean方法参数校验：org.springframework.validation.beanvalidation.MethodValidationPostProcessor

7、Spring有哪些校验核心组件
- 校验器：`org.springframework.validation.Validator`
- 错误收集器：`org.springframework.validation.Errors`
- Java Bean错误描述：`org.springframwork.validation.ObjectError`
- Java Bean属性错误描述：`org.springframework.validation.FieldError`
- Bean Validation适配：`org.springframework.validation.beanvalidation.LocalBeanValidatorFactoryBean`