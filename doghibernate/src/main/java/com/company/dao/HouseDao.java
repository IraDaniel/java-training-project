package com.company.dao;

import com.company.entity.Dog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collection;

public class HouseDao {

    private final SessionFactory sessionFactory;

    public HouseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Collection<Dog> get() {
        Query query = getCurrentSession().createNamedQuery("getAllHouses");
        return query.list();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
