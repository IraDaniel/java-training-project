package com.company.service.impl;


import com.company.dao.JdbcConnectionHolder;
import com.company.exception.DogSqlException;
import com.company.service.DogService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionalDynamicProxy implements InvocationHandler {

    private DogService invocationTarget;
    private final JdbcConnectionHolder connectionHolder;

    public TransactionalDynamicProxy(DogService invocationTarget, JdbcConnectionHolder connectionHolder) {
        this.invocationTarget = invocationTarget;
        this.connectionHolder = connectionHolder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            result = method.invoke(invocationTarget, args);
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static DogService newInstance(DogService invocationTarget, JdbcConnectionHolder connectionHolder) {
        return (DogService) Proxy.newProxyInstance(invocationTarget.getClass().getClassLoader(), invocationTarget.getClass().getInterfaces(),
                new TransactionalDynamicProxy(invocationTarget, connectionHolder));
    }
}
