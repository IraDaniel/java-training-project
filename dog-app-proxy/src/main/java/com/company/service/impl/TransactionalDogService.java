package com.company.service.impl;

import com.company.dao.JdbcConnectionHolder;
import com.company.entity.Dog;
import com.company.exception.DogException;
import com.company.exception.DogNotFoundException;
import com.company.exception.DogSqlException;
import com.company.service.DogService;

import java.util.Collection;
import java.util.UUID;

import static com.company.utility.JdbcDogDaoUtils.DOG_DOES_NOT_EXIST;


public class TransactionalDogService implements DogService {


    private final DogService delegate;
    private final JdbcConnectionHolder connectionHolder;

    public TransactionalDogService(DogService dogService, JdbcConnectionHolder connectionHolder) {
        this.delegate = dogService;
        this.connectionHolder = connectionHolder;
    }

    public Dog create(Dog dog) {
        Dog created;
        try {
            created = delegate.create(dog);
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
        return created;
    }

    public Dog update(Dog dog) {
        Dog updated;
        try {
            updated = delegate.update(dog);
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
        return updated;
    }

    public Dog get(UUID dogUuid) {
        Dog dog;
        try {
            dog = delegate.get(dogUuid);
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
        if (dog == null) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dogUuid));
        }
        return dog;
    }

    public Collection<Dog> getAll() {
        Collection<Dog> dogs;
        try {
            dogs = delegate.getAll();
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
        return dogs;
    }

    public void delete(UUID dogUuid) {
        try {
            delegate.delete(dogUuid);
            connectionHolder.commit();
        } catch (DogException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
    }
}
