package com.example.testall.beanvalidation;

import javax.validation.constraints.NotNull;

public class Car {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
