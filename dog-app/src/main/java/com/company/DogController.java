package com.company;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "dog")
public class DogController {

    private static final String DOG_DOES_NOT_EXIST = "Dog with id %s does not exist";
    private static Map<UUID, Dog> dogs = new ConcurrentHashMap<UUID, Dog>();

    static {
        Calendar c = Calendar.getInstance();
        c.set(2015, Calendar.DECEMBER, 10);
        Dog dog1 = new Dog(UUID.randomUUID(), "puppy", c, 12, 12);
        dogs.put(dog1.getId(), dog1);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dog> get() {
        System.out.println("Call get");
        return dogs.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog get(@PathVariable UUID id) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, id));
        }
        return dogs.get(id);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog create(@Valid @RequestBody Dog dog) {
        dog.setId(UUID.randomUUID());
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog update(@Valid @RequestBody Dog dog) {
        if (!dogs.containsKey(dog.getId())) {
            throw new DogNotFoundException(String.format(DOG_DOES_NOT_EXIST, dog.getId()));
        }

        return dogs.put(dog.getId(), dog);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Dog delete(@PathVariable UUID id) {
        Dog removed = dogs.remove(id);
        return removed;
    }
}
