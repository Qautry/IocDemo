package com.lemon.ioc.library;

import android.app.Activity;
import android.view.View;

import com.lemon.ioc.library.annotations.ContentView;
import com.lemon.ioc.library.annotations.EventBase;
import com.lemon.ioc.library.annotations.InjectView;
import com.lemon.ioc.library.listener.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {
    public static void inject(Activity activity){
        //布局注入
        injectLayout(activity);
        //控件注入
        injectViews(activity);
        //事件注入
        injectEvents(activity);
    }

    private static void injectLayout(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null){
            //获取到布局的值（R.layout.activity_main）
            int layoutId = contentView.value();
            //第一种方式：
            //activity.setContentView(layoutId);
            try {
                //获取方法：setContentView(R.layout.activity_main)
                Method method = clazz.getMethod("setContentView", int.class);
                //执行方法
                method.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void injectViews(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        //循环每个属性
        for(Field field : fields){
            //获取属性上的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if(injectView != null){ //并不是所有的属性都有注解
                //获取注解的值
                int viewId = injectView.value();
                try {
                    //获取方法：findViewById
                    Method method = clazz.getMethod("findViewById", int.class);
                    //第一种写法
                    // Object view = activity.findViewById(R.id.tv);
                    //执行方法,得到View对象
                    Object view = method.invoke(activity,viewId);

                    //设置该属性可以访问，哪怕是private修饰的
                    field.setAccessible(true);
                    //属性的值赋给当前Activity的控件
                    //注意：当属性为private，赋值闪退
                    field.set(activity,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private static void injectEvents(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取当前所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        //遍历所有的方法
        for(Method method : methods){
            //获取每个方法的注解，可能多个注解
            Annotation[] annotations= method.getDeclaredAnnotations();
            //遍历每个方法的注解
            for (Annotation annotation : annotations){
                //获取OnClick注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if(annotationType != null){
                    EventBase evenBase = annotationType.getAnnotation(EventBase.class);
                    //事件3个重要成员
                    String listenerSetter = evenBase.listenerSetter();
                    Class<?> listenerType = evenBase.listenerType();
                    String callBackListener = evenBase.callBackListener();
                    try {
                        //通过annotationType获取onClick注解的Value值，拿到R.id.XX
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethod(callBackListener,method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        for (int viewId : viewIds){
                            //获取View
                            View view = activity.findViewById(viewId);
                            //获取方法
                            Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                            setter.invoke(view,listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }






}
