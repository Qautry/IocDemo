package com.lemon.ioc.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) //该注解作用于属性之上
@Retention(RetentionPolicy.RUNTIME) //JVM运行时通过反射获取该注解
public @interface InjectView {
    int value();
}
