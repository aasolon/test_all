package com.example.testall.factory;

import org.springframework.stereotype.Component;

@Component
public class SomeBuilderOneImpl implements SomeBuilder {

    @Override
    public SomeBuilderType getType() {
        return SomeBuilderType.ONE;
    }

    @Override
    public String build() {
        return "ONE";
    }
}
