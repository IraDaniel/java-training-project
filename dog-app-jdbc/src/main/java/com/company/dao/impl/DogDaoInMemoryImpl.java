package com.company.dao.impl;

import com.company.dao.DogDao;
import com.company.entity.Dog;
import com.company.exception.DogException;
import com.company.exception.DogNotFoundException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DogDaoInMemoryImpl implements DogDao {

    static final String DOG_ALREADY_EXISTS = "Dog with id %s already exists";
    static final String DOG_DOES_NOT_EXIST = "Dog with id %s does not exist";

    private static Map<UUID, Dog> dogs = new ConcurrentHashMap<>();

    static {
        Dog dog1 = new Dog(UUID.randomUUID(), "puppy", initDate(2015, Calendar.DECEMBER, 10), 12, 12);
        Dog dog2 = new Dog(UUID.randomUUID(), "to_find_puppy", initDate(2013, Calendar.DECEMBER, 10), 12, 12);
        dogs.put(dog1.getId(), dog1);
        dogs.put(dog2.getId(), dog2);
    }

    public Dog create(Dog dog) {
        if (dog.getId() != null && dogs.containsKey(dog.getId())) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getId()), HttpStatus.CONFLICT);
        }
        dog.setId(UUID.randomUUID());
        dogs.put(dog.getId(), dog);
        return dog;
    }

    public Dog update(Dog dog) {
        if (!dogs.containsKey(dog.getId())) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dog.getId()));
        }

        dogs.put(dog.getId(), dog);
        return dog;
    }

    public Dog get(UUID dogUuid) {
        if (!dogs.containsKey(dogUuid)) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dogUuid));
        }
        return dogs.get(dogUuid);
    }

    public Collection<Dog> get() {
        return dogs.values();
    }

    public void delete(UUID dogUuid) {
        dogs.remove(dogUuid);
    }

    private static LocalDate initDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }
}