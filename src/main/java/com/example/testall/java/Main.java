package com.example.testall.java;

import org.apache.commons.lang3.time.DateUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        testComputeIfAbsent();

//        testSwitchCase();

//        testSwitchCase2();

//        testComparator();

        int i1 = 0;
    }

    public static void testComputeIfAbsent() {
        Map<String, String> map = new HashMap<>();
        Function<String, String> mappingFunction = key -> {
            System.out.println("mappingFunction called");
            return key + "_" + UUID.randomUUID();
        };
        map.computeIfAbsent("1", mappingFunction);
        map.computeIfAbsent("1", mappingFunction);
        String s = map.computeIfAbsent(null, mappingFunction);
        int i = 0;
    }

    public static void testSwitchCase() {
        String vat = "A";
        String result = "";
        switch (vat) {
            case "INCLUDED":
                result = "INCLUDED";
                break;
            case "MANUAL":
                result = "MANUAL";
                break;
            case "NO_VAT":
                result = "WITHOUT_VAT";
                break;
            case "ONTOP":
                result = "ONTOP";
                break;
        }
    }

    public static void testSwitchCase2() {
        Type type = null;
        String result = "";
        switch (type) {
            case TYPE_1:
                result = "INCLUDED";
                break;
            case TYPE_2:
                result = "MANUAL";
                break;
            default:
                result = "111";
        }

        int i = 0;
    }

    private static void testComparator() {
        List<SomeObject> someObjects = new ArrayList<>();

        SomeObject someObject1 = new SomeObject();
        someObject1.setList(Arrays.asList(1, 10));

        SomeObject someObject2 = new SomeObject();
        someObject2.setList(Arrays.asList(1, 10, 2));

        someObjects.add(someObject1);
        someObjects.add(someObject2);

        SomeObject someObjectWithMaxVersion = someObjects.stream()
                .max((e1, e2) -> VERSION_COMPARATOR.compare(e1.getList(), e2.getList())).orElse(null);
        SomeObject someObject = someObjects.stream()
                .max(Comparator.comparing(SomeObject::getName)).orElse(null);

    }

    public static Date getNextWorkDay(Date date) {
        Date nextWorkDay = date;
        nextWorkDay = DateUtils.addDays(nextWorkDay, 1);
        return nextWorkDay;
    }

    private static class SomeObject {
        private List<Integer> list;
        private String name;

        public List<Integer> getList() {
            return list;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static final Comparator<List<Integer>> VERSION_COMPARATOR = (list1, list2) -> {
        for (int i = 0; i < Math.max(list1.size(), list2.size()); i++) {
            Integer lhs = list1.get(i);
            Integer rhs = list2.get(i);
//            Integer lhs = i < list1.size() ? list1.get(i) : 0;
//            Integer rhs = i < list2.size() ? list2.get(i) : 0;
            if (lhs == null && rhs == null) {
                return 0;
            }
            if (lhs == null) {
                return -1;
            }
            if (rhs == null) {
                return 1;
            }
            int value = Integer.compare(lhs, rhs);
            if (value != 0) {
                return value;
            }
        }
        return 0;
    };

    private enum Type {
        TYPE_1, TYPE_2;
    }
}
