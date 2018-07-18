package com.company;


import com.company.entity.*;

import java.util.*;

public interface DogDao {
    Dog create(Dog dog);

    Dog update(Dog dog);

    Dog get(UUID dogUuid);

    Collection<Dog> get();

    void delete(UUID dogUuid);
}
