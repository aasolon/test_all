package com.example.testall.factory;

import org.springframework.stereotype.Component;

@Component
public class SomeBuilderTwoImpl implements SomeBuilder {

    @Override
    public SomeBuilderType getType() {
        return SomeBuilderType.TWO;
    }

    @Override
    public String build() {
        return "TWO";
    }
}
