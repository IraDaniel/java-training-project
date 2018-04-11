package com.company.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;


public class Dog {

    private UUID id;
    @NotNull
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past
    private Date birthDay;
    @Min(1)
    private int weight;
    @Min(1)
    private int height;

    public Dog() {
    }

    public Dog(UUID id, String name, Date birthDay, int weight, int height) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.weight = weight;
        this.height = height;
    }

    public Dog(String name, Date birthDay, int weight, int height) {
        this.name = name;
        this.birthDay = birthDay;
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}

