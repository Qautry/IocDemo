package com.lemon.ioc.library.annotations;

import android.view.View;
import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnItemLongClickListener",listenerType = AdapterView.OnItemLongClickListener.class,callBackListener = "onItemLongClick")
public @interface OnItemLongClick {
    int[] value();
}
