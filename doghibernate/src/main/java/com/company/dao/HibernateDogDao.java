package com.company.dao;

import com.company.*;
import com.company.entity.*;
import org.hibernate.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

/**
 * Created by Irina_Daniel on 7/17/2018.
 */
public class HibernateDogDao implements DogDao {

    private final SessionFactory sessionFactory;

    public HibernateDogDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Dog create(Dog dog) {
        dog.setId(UUID.randomUUID());
        getCurrentSession().save(dog);
        return dog;
    }

    @Override
    public Dog update(Dog dog) {
        getCurrentSession().update(dog);
        return dog;
    }

    @Override
    public Dog get(UUID dogUuid) {
        return null;
    }

    @Override
    public Collection<Dog> get() {
        return null;
    }

    @Override
    public void delete(UUID dogUuid) {
        Dog dog = (Dog) getCurrentSession().load(Dog.class, dogUuid);
        getCurrentSession().delete(dog);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    void flushAndClear() {
        getCurrentSession().flush();
        getCurrentSession().clear();
    }
}
