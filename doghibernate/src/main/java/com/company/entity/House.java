package com.company.entity;


import java.util.UUID;


public class House {

    private UUID id;
    private String name;

    public House() {
    }

    public House(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
