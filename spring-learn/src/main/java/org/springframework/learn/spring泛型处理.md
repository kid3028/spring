### Spring泛型处理
- Java泛型基础
- Java 5类型接口
- Spring泛型类型辅助类
- Spring泛型集合类型辅助类
- Spring方法参数封装MethodParameter
- Spring 4.2泛型优化实现ResolvableType
- ResolvableType的局限性
- 面试题


1、Java泛型基础
1.1、泛型类型
泛型类型时在类型上参数化的泛型类或者接口

1.2、泛型使用场景
- 编译时强类型检查
- 避免类型强转
- 实现通用算法

1.3、泛型类型擦写
- 泛型被引入到java语言中，以便在编译时提供更严格的类型检查并支持泛型编程。类型擦除确保不会为参数化类型创建新类；
因此，泛型不会产生运行时开销。为了实现泛型，编译器将类型擦除应用于：
 - 将泛型类型中的所有类型参数替换为其边界，如果类型参数是无边界的，则将其替换为Object。因此，生成的字节码只包含普通类、接口、方法
 - 必要时插入类型转换以保持类型安全
 - 生成桥方法以保留扩展泛型类型中多态性 

``` java
List<String> list = new ArrayList<>();
// list.add(1);
// 泛型擦写，欺骗编译器
Collection collection = list;
collection.add(1);
```

2、Java5类型接口
2.1、Java 5类型接口 `java.lang.reflect.Type`
|派生类或接口|说明|
|---|---|
|java.lang.Class|Java类api，如果java.lang.String|
|java.lang.reflect.GenericArrayType|泛型数组类型`[]T`|
|java.lang.reflect.ParameterizedType|泛型参数类型|
|java.lang.reflect.TypeVariable|泛型类型变量，如Collection<E>中的E|
|java.lang.reflect.WildcardType|泛型通配类型`?` `? extends E`|

2.2、泛型反射Api
|类型|api|
|---|---|
|泛型信息(Generic Info)|java.lang.Class#getGenericInfo()|
|泛型参数(Parameters)|java.lang.reflect.ParameterizedType|
|泛型父类(Super Classes)|java.lang.Class#getGenericSuperClas()|
|泛型接口(Interfaces)|java.lang.Class#getGenericInterfaces()|
|泛型声明(Generics Declaration)|java.lang.reflect.GenericDeclaration|

3、Spring泛型类型辅助类
- 核心Api`org.springframework.core.GenericTypeResolver`
- 版本支持：[2.5.2,)
- 处理类型相关(Type)相关方法`resolveReturnType` `resolveType`
- 处理泛型参数类型(ParameterizedType)相关方法 `resolveReturnTypeArgument` `resolveTypeArgument` `resolveTypeArguments`
- 处理泛型类型变量(TypeVariable)相关方法 `getTypeVariableMap`

4、Spring泛型集合类型辅助类
- 核心Api`org.springframework.core.GenericCollectionTypeResolver`
- 版本支持：[2.0, 4.3]
- 替换实现：`org.springframework.core.ResolvableType`
- 处理Collect相关 `getCollection*Type`
- 处理Map相关 `getMapKey*Type`  `getMapValue*Type`

5、Spring方法参数封装
- 核心api：`org.springframework.core.MethodParameter`
- 起始版本: [2.0,)
- 类信息：
 - 关联的方法：Method
 - 关联的构造器：Constructor
 - 构造器或方法参数索引：parameterIndex（java静态语言，参数个数确定）
 - 构造器或方法参数类型：parameterType
 - 构造器或方法参数泛型类型：genericParameterType
 - 构造器或者方法参数参数名称：parameterName(java8开始，以前编译后是不会记录参数名称)
 - 所在的类： containingClass

6、Spring 4.0泛型优化实现——ResolvableType
- 核心Api：`org.springframework.core.ResolvableType`
- 起始版本：[4.0,)
- 扮演角色：`GenericTypeResolver` `GenericCollectionTypeResolver`替代着
- 工厂方法： for*方法
- 转换方法：as*方法
- 处理方法：resolve*方法

7、ResolvableType的局限性
- 局限一：ResolvableType无法处理泛型擦写
- 局限二：ResolvableType无法处理非具体化的ParameterizedType
    
8、java泛型擦写发生在编译时还是运行时
运行时

9、ResolvableType的设计优势
- 简化Java Type Api开发，屏蔽复杂Api的运用，如ParameterizedType
- 不变性设计(Immutability)
- Fluent Api设计(Builder模式)，链式(流式)编程




























