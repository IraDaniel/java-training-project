package com.company.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;


public class Dog {

    private UUID uuid;

    @Size(min = 1, max = 100)
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

    public Dog(UUID uuid, String name, Date birthDay, int weight, int height) {
        this.uuid = uuid;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

