Spring Bean作用域
- Spring Bean作用域
- singleton bean作用域
- prototype bean作用域
- request bean作用域
- session bean作用域
- application bean作用域
- 自定义bean作用域

1、spring bean作用域
|来源|说明|
|---|---|
|singleton|默认spring bean作用域，一个BeanFactory中有且仅有一个实例|
|prototype|原型作用域，每次依赖查找，依赖注入生成新的bean对象|
|session|将spring bean存储在HttpSession中|
|application|将spring bean存储在ServletContext中|

2、singleton bean、prototype bean
2.1、singleton bean无论依赖查找还是依赖注入，均为同一个对象
     prototype bean无论依赖查找还是依赖注入，都是新生成的对象
2.2、如果依赖注入集合类型对象，singleton bean和prototype bean均会存在一个
2.3、spring容器没办法管理prototype的完整生命周期，也没办法记录实例的存在。销毁回调方法将不会执行，可以利用`BeanPostProcessor`进行清扫工作
     singleton和prototype都会执行初始化方法回调，但仅singleton会回调销毁方法

3、自定义Bean作用域
3.1、实现Scope：`org.springframework.beans.factory.config.Scope`
3.2、注册Scope:
- Api：`org.springframework.beans.factory.config.ConfigurableBeanFactory#registerScope`
- 配置：
```xml
<bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
     <property name="scopes">
          <map>
               <entry key="...">
                    
               </entry>
          </map>
     </property>
</bean>
```

5、scope扩展
SpringCloud `@RefreshScope`  `RefreshScope.class`  