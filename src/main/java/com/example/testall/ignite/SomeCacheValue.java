package com.example.testall.ignite;

import java.io.Serializable;

public class SomeCacheValue {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CacheValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
