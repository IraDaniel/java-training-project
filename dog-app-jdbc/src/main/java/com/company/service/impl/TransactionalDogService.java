package com.company.service.impl;

import com.company.dao.DogDao;
import com.company.dao.JdbcConnectionHolder;
import com.company.entity.Dog;
import com.company.exception.DogException;
import com.company.exception.DogNotFoundException;
import com.company.exception.DogSqlException;
import com.company.service.DogService;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static com.company.utility.JdbcDogDaoUtils.DOG_ALREADY_EXISTS;
import static com.company.utility.JdbcDogDaoUtils.DOG_DOES_NOT_EXIST;


public class TransactionalDogService implements DogService {


    private final DogDao dogDao;
    private final JdbcConnectionHolder connectionHolder;

    public TransactionalDogService(DogDao dogDao, JdbcConnectionHolder connectionHolder) {
        this.dogDao = dogDao;
        this.connectionHolder = connectionHolder;
    }

    public Dog create(Dog dog) {
        if (dog.getId() != null && dogDao.get(dog.getId()) != null) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        Dog created;
        try {
            created = dogDao.create(dog);
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
        if (dog.getId() != null && get(dog.getId()) == null) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dog.getId()));
        }
        Dog updated;
        try {
            updated = dogDao.create(dog);
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
            dog = dogDao.get(dogUuid);
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
        Collection<Dog> dogs = new ArrayList<>();
        try {
            dogs = dogDao.get();
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
            dogDao.delete(dogUuid);
            connectionHolder.commit();
        } catch (DogSqlException e) {
            connectionHolder.rollBack();
            throw e;
        } finally {
            connectionHolder.closeConnection();
        }
    }
}
