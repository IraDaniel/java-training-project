package com.company;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("dog")
public class DogController {

    private static Map<UUID, Dog> dogs = new HashMap<UUID, Dog>();

    static {
        Calendar c = Calendar.getInstance();
        c.set(2015, Calendar.DECEMBER, 10);
        Dog dog1 = new Dog(UUID.randomUUID(), "puppy", c, 12, 12);
        dogs.put(dog1.getId(), dog1);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dog> getAll() {
        System.out.println("Call get");
        return dogs.values();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog get(@PathVariable UUID id) {
        return dogs.containsKey(id) ? dogs.get(id) : null;
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog create(@Valid @RequestBody Dog dog) {
        if (dog.getId() == null) {
            dog.setId(UUID.randomUUID());
            dogs.put(dog.getId(), dog);
        }
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog update(@Valid @RequestBody Dog dog) {
        if (!dogs.containsKey(dog.getId())) {
            throw new DogNotFoundException(String.format("Dog with id %s does not exist", dog.getId()));
        }

        return dogs.put(dog.getId(), dog);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable UUID id) {
        Dog removed = dogs.remove(id);
    }
}
