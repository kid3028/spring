package org.springframework.learn.ioc.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * 扩张@Autowired
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Autowired
public @interface MyAutowired {

	boolean required() default true;

}
