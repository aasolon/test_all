package com.example.testall.factory;

import com.google.common.base.Functions;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SomeBuilderFactory {

    private final Map<SomeBuilderType, SomeBuilder> builders;

    public SomeBuilderFactory(List<SomeBuilder> builders) {
//        this.builders = builders
//                .stream()
//                .collect(Collectors.toUnmodifiableMap(SomeBuilder::getType, x -> x));

        Function<SomeBuilder, SomeBuilderType> getType = SomeBuilder::getType;
        Map<SomeBuilderType, SomeBuilder> buildersMap = builders
                .stream()
                .collect(Collectors.toMap(
                        getType,
                        Function.identity(),
                        (value1, value2) -> {
                            throw new IllegalArgumentException("Duplicate keys " + getType.apply(value1));
                        },
                        () -> new EnumMap<>(SomeBuilderType.class)));
        this.builders = Collections.unmodifiableMap(buildersMap);
        int i = 0;
    }

    public SomeBuilder getBuilder(SomeBuilderType type) {
        SomeBuilder responseBuilder = builders.get(type);
        if (responseBuilder == null) {
            throw new IllegalArgumentException(type.name());
        }
        return responseBuilder;
    }
}
