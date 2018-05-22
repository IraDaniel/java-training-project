package com.company.dao;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DogDaoDynamicProxy implements InvocationHandler {
    private Object obj;

    public DogDaoDynamicProxy(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(obj, args) ;
    }
}
