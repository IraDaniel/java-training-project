package com.company.controller;

import com.company.entity.Dog;
import com.company.exception.DogException;
import com.company.exception.DogNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "dog")
public class DogController {
    private static final Logger LOGGER = LogManager.getLogger(DogController.class);

    private static final String DOG_DOES_NOT_EXIST = "Dog with id %s does not exist";
    private static final String DOG_ALREADY_EXISTS = "Dog with id %s already exists";
    private static Map<UUID, Dog> dogs = new ConcurrentHashMap<UUID, Dog>();

    static {
        Dog dog1 = new Dog(UUID.randomUUID(), StringUtils.repeat("c", 3), initDate(2015, Calendar.DECEMBER, 10), 12, 12);
        Dog dog2 = new Dog(UUID.randomUUID(), StringUtils.repeat("a", 3), initDate(2013, Calendar.DECEMBER, 10), 12, 12);
        Dog dog3 = new Dog(UUID.randomUUID(), StringUtils.repeat("b", 3), initDate(2014, Calendar.DECEMBER, 10), 12, 12);
        dogs.put(dog1.getUuid(), dog1);
        dogs.put(dog2.getUuid(), dog2);
        dogs.put(dog3.getUuid(), dog3);
        LOGGER.info("Create dogs:" + dogs.entrySet().toString());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dog> get() {
        return dogs.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog get(@PathVariable UUID id) {
        LOGGER.info("Get by UUID:" + id);
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, id));
        }
        return dogs.get(id);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog create(@Valid @RequestBody Dog dog) {
        if (dog.getUuid() != null && dogs.containsKey(dog.getUuid())) {
            throw new DogException(String.format(DOG_ALREADY_EXISTS, dog.getUuid()), HttpStatus.CONFLICT);
        }
        dog.setUuid(UUID.randomUUID());
        dogs.put(dog.getUuid(), dog);
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog update(@Valid @RequestBody Dog dog) {
        if (!dogs.containsKey(dog.getUuid())) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dog.getUuid()));
        }
        return dogs.put(dog.getUuid(), dog);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Dog delete(@PathVariable UUID id) {
        Dog removed = dogs.remove(id);
        return removed;
    }

    private static Date initDate(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);
        return c.getTime();
    }
}
