package com.company.dao;


import com.company.entity.Dog;

import java.util.Collection;
import java.util.UUID;

public interface DogDao {
    Dog create(Dog dog);

    Dog update(Dog dog);

    Dog get(UUID dogUuid);

    Collection<Dog> get();

    void delete(UUID dogUuid);
}
