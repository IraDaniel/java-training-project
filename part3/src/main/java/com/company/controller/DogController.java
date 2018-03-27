package com.company.controller;

import com.company.entity.Dog;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("dog")
public class DogController {

    private static Map<UUID, Dog> dods = new HashMap<UUID, Dog>();

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Dog> getAll() {
        return dods.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Dog get(@PathVariable UUID id) {
        return dods.containsKey(id) ? dods.get(id) : null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Dog create(@RequestBody Dog dog) {
        if (dog.getId() == null) {
            dog.setId(UUID.randomUUID());
            dods.put(dog.getId(), dog);
        }
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Dog update(@RequestBody Dog dog) {
        return dods.containsKey(dog.getId()) ? dods.put(dog.getId(), dog) : null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
    }


}
