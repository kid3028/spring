### @Bean处理流程
1、解析范围：ConfigurationClass中的@Bean方法
`ConfigurationClassPostProcessor`
`ConfigurationClassParser`
在`AnnotationConfigUtils`中进行bean注册

2、方法类型：静态@Bean方法和实例@Bean方法