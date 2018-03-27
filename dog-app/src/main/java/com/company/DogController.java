package com.company;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DogController {

    private static Map<UUID, Dog> dogs = new HashMap<UUID, Dog>();

    @RequestMapping(value = "dog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dog> getAll() {
        return dogs.values();
    }

    @RequestMapping(value = "dog/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog get(@PathVariable UUID id) {
        return dogs.containsKey(id) ? dogs.get(id) : null;
    }

    @RequestMapping(value = "dog", method = RequestMethod.POST)
    public Dog create(@RequestBody Dog dog) {
        if (dog.getId() == null) {
            dog.setId(UUID.randomUUID());
            dogs.put(dog.getId(), dog);
        }
        return dog;
    }

    @RequestMapping(value = "dog/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog update(@RequestBody Dog dog) {
        return dogs.containsKey(dog.getId()) ? dogs.put(dog.getId(), dog) : null;
    }

    @RequestMapping(value = "dog/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable UUID id) {
        Dog removed = dogs.remove(id);
    }


}
