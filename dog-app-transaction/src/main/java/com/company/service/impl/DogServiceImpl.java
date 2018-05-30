package com.company.service.impl;

import com.company.aspect.TransactionalMarker;
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
    @TransactionalMarker
    public Dog create(Dog dog) {
        return dogDao.create(dog);
    }
    @TransactionalMarker
    public Dog update(Dog dog) {
       return dogDao.update(dog);
    }
    @TransactionalMarker
    public Dog get(UUID dogUuid) {
       return dogDao.get(dogUuid);
    }
    @TransactionalMarker
    public Collection<Dog> getAll() {
        return dogDao.get();
    }
    @TransactionalMarker
    public void delete(UUID dogUuid) {
        dogDao.delete(dogUuid);
    }
}
