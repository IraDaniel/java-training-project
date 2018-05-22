package com.company.service.impl;


import com.company.dao.JdbcConnectionHolder;
import com.company.exception.DogSqlException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class CglibTransactionalDogService implements InvocationHandler {
    private DogService invocationTarget;
    private final JdbcConnectionHolder connectionHolder;

    public CglibTransactionalDogService(DogService invocationTarget, JdbcConnectionHolder connectionHolder) {
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
    public static DogService create(DogService invocationTarget, JdbcConnectionHolder connectionHolder) {
        CglibTransactionalDogService call = new CglibTransactionalDogService(invocationTarget, connectionHolder);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DogService.class);
        enhancer.setCallback(call);
        DogService proxy = (DogService) enhancer.create();
        return proxy;
    }
}
