package com.company.beans;

import org.springframework.stereotype.Component;

/**
 * Created by Ira on 21.03.2018.
 */
@Component
public class Book {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println("Book name:" + name);
    }
}
