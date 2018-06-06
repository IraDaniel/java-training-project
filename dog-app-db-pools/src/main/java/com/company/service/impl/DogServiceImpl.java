package com.company.service.impl;

import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.service.DogService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;


public class DogServiceImpl implements DogService {
    private final DogDao dogDao;

    public DogServiceImpl(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    @Transactional
    public Dog create(Dog dog) {
        return dogDao.create(dog);
    }

    @Transactional
    public Dog update(Dog dog) {
        //check that dog exist
        dogDao.get(dog.getId());
        return dogDao.update(dog);
    }

    @Transactional
    public Dog get(UUID dogUuid) {
        return dogDao.get(dogUuid);
    }

    @Transactional
    public Collection<Dog> getAll() {
        return dogDao.get();
    }

    @Transactional
    public void delete(UUID dogUuid) {
        dogDao.delete(dogUuid);
    }
}
