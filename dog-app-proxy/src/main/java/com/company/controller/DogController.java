package com.company.controller;


import com.company.entity.Dog;
import com.company.service.impl.TransactionalDogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "dog")
public class DogController {

    private TransactionalDogService dogService;

    @Autowired
    public DogController(TransactionalDogService dogService) {
        this.dogService = dogService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dog> get() {
        return dogService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog get(@PathVariable UUID id) {
        return dogService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog create(@Valid @RequestBody Dog dog) {
        return dogService.create(dog);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog update(@Valid @RequestBody Dog dog) {
        return dogService.update(dog);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable UUID id) {
        dogService.delete(id);
    }
}

