package com.lemon.ioc.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE) //元注解，作用在注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //事件的3个重要成员

    //1.setOnxxxListener
    String listenerSetter();
    //2.监听的对象，View.OnXXXListener
    Class<?> listenerType();
    //3.回调方法：onClick(View view):
    String callBackListener();
}
