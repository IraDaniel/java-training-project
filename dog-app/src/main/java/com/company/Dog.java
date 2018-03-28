package com.company;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.UUID;


public class Dog {

    private UUID id;
    private String name;
//    private Calendar birthDay;
    @NotNull
    private int weight;
    @NotNull
    private int height;

    public Dog() {
    }

    public Dog(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dog(UUID id, String name, Calendar birthDay, @NotNull int weight, @NotNull int height) {
        this.id = id;
        this.name = name;
     //   this.birthDay = birthDay;
        this.weight = weight;
        this.height = height;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

