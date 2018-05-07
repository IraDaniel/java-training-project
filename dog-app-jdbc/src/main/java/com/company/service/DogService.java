package com.company.service;


import com.company.entity.Dog;

import java.util.Collection;
import java.util.UUID;

public interface DogService {

    Dog create(Dog dog);

    Dog update(Dog dog);

    Dog get(UUID dogUuid);

    Collection<Dog> getAll();

    void delete(UUID dogUuid);
}
