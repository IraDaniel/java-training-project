package com.company.service.impl;

import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.service.DogService;

import java.util.Collection;
import java.util.UUID;

public class DogServiceImpl implements DogService {

    private final DogDao dogDao;

    public DogServiceImpl(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    public Dog create(Dog dog) {
        return dogDao.create(dog);
    }

    public Dog update(Dog dog) {
       return dogDao.update(dog);
    }

    public Dog get(UUID dogUuid) {
       return dogDao.get(dogUuid);
    }

    public Collection<Dog> getAll() {
        return dogDao.get();
    }

    public void delete(UUID dogUuid) {
        dogDao.delete(dogUuid);
    }
}
