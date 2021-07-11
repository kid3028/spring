### 为什么ObjectFactory提供的是延迟依赖查找
1、原因
- ObjectFactory(或ObjectProvider)可以关联某一类型Bean
- ObjectFactory和ObjectProvider对象在被依赖注入和依赖查询时并未实时查找关联类型的Bean
- 当ObjectFactory(或ObjectProvider)调用getObject()时目标Bean才能被依赖查找

2、总结
ObjectFactory(或ObjectProvider)相当于某一类型Bean依赖查找代理对象