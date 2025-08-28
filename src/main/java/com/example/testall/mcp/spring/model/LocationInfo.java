package com.example.testall.mcp.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class LocationInfo {

//    @javax.annotation.Nonnull
    @JsonPropertyDescription("Описание поля city_111")
    @JsonProperty(value = "city_111", required = true)
    private String city;

    private int someNumber;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }
}
