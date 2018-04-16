package com.company.dao;

import com.company.entity.Dog;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Ira on 16.04.2018.
 */
public interface DogDao {

    Dog create(Dog dog);
    Dog update(Dog dog);
    Dog get(UUID dogUuid);
    Collection<Dog> get();
    Dog delete(UUID dogUuid);
}
