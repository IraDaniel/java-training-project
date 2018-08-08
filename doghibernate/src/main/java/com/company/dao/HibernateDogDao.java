package com.company.dao;

import com.company.*;
import com.company.entity.*;
import com.company.exception.DogNotFoundException;
import org.hibernate.*;
import org.hibernate.query.Query;
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
        Dog dog = getCurrentSession().get(Dog.class, dogUuid);
        if (dog == null) throw new DogNotFoundException(dogUuid);
        return dog;
    }

    @Override
    public Collection<Dog> get() {
        Query query = getCurrentSession().createNamedQuery("getAllDogs");
        return query.list();
    }

    @Override
    public void delete(UUID dogUuid) {
        Dog dog = (Dog) getCurrentSession().load(Dog.class, dogUuid);
        getCurrentSession().delete(dog);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void flushAndClear() {
        getCurrentSession().flush();
        getCurrentSession().clear();
    }
}
