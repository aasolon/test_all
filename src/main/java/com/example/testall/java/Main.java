package com.example.testall.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.InternetDomainName;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final FastDateFormat DATE_TIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");


    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {
        testParseUrl();

//        testUpgVersionSublist();

//        testJavaSerialization();

//        testJackson();

//        testFile();

//        testLongEquals();

//        testStringJoin();

//        testFastDateFormat();

//        testRegExp();

//        testComputeIfAbsent();

//        testSwitchCase();

//        testSwitchCase2();

//        testComparator();

//        testComparator2();

        int i1 = 0;
    }

    private static void testParseUrl() throws URISyntaxException {
        String forbiddenDomainsStr = "localhost,127.0.0.1,sberbank.ru";
//        String forbiddenDomainsStr = "localhost,127.0.0.1,sberbank.ru";
        String[] forbiddenDomains = forbiddenDomainsStr.split(",");

//        String urlString = "https://a.b.test.Sberbank.ru:8080/mvc";
//        String urlString = "https://asd.basda.test.Sberbank.com:8080/mvc";
        String urlString = "https://127.0.0.1:8080/mvc";
//        String urlString = "https://x.adwords.google.co.uk:8080/mvc";
//        String urlString = "https://localhost:8080/mvc";

        URI uri = new URI(urlString);
        String host = uri.getHost();
        if (Arrays.stream(forbiddenDomains).anyMatch(host::equalsIgnoreCase)) {
            throw new IllegalArgumentException("1");
        }
        String hostTopPrivateDomain = InternetDomainName.from(host).topPrivateDomain().toString();
        if (Arrays.stream(forbiddenDomains).anyMatch(forbiddenDomain -> hostTopPrivateDomain.equalsIgnoreCase(forbiddenDomain))) {
            throw new IllegalArgumentException("1");
        }

        int i = 0;
    }

    private static void testUpgVersionSublist() {
        List<Integer> REVERSE_SORTED_VERSIONS = Arrays.asList(35, 34, 33, 32);
        List<Integer> beforeVersions = REVERSE_SORTED_VERSIONS.subList(REVERSE_SORTED_VERSIONS.indexOf(33), REVERSE_SORTED_VERSIONS.size());
        int i = 0;
    }

    private static void testJavaSerialization() throws IOException, ClassNotFoundException {
//        SomeSerializableObject someSerializableObject = new SomeSerializableObject("1", "2", "3", "4", "5", "6");

        String fileName = "serialized_in_file_some_object.txt";

//        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeObject(someSerializableObject);
//        objectOutputStream.flush();
//        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SomeSerializableObject someSerializableObject_2 = (SomeSerializableObject) objectInputStream.readObject();
        objectInputStream.close();

        int i = 0;
    }

    private static class Person {
        private int count;
        private String name;
        private boolean isEnabled;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        public void setEnabled(boolean enabled) {
            isEnabled = enabled;
        }
    }

    private static void testJackson() throws JsonProcessingException {
        String str = "{\"count\": 11, \"name\": \"Vasya\"}";
        Person person = new ObjectMapper().readValue(str, Person.class);

        String str2 = "{\"count\": 22, \"name\": \"Petya\", \"enabled\": true}";
        Person person2 = new ObjectMapper().readValue(str2, Person.class);

        int i = 0;
    }

    private static void testFile() throws IOException {
        Path path = Paths.get("com/bssys/response.xsd");
        boolean exists = Files.exists(path, new LinkOption[0]);

        try (InputStream in = Main.class.getResourceAsStream("com/bssys/response.xsd");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            // Use resource
            String s = reader.readLine();
            int i = 0;
        }

        int i = 0;
    }

    private static void testLongEquals() {
        Long a = 1L;
        Long b = null;
        a.equals(b);
    }

    private static void testStringJoin() {
        String join = String.join(";", null, null);
        System.out.println("Join result = " + join);
    }

    private static void testFastDateFormat() {
        Date date = new Date();
        String format = DATE_TIME_FORMAT.format(date);
        System.out.println("Formatted date = " + format);
    }

    private static void testRegExp() {
        Pattern p = Pattern.compile("[0-9а-яА-ЯёË]*");
        Matcher m = p.matcher("Артём");
        boolean b = m.matches();
        System.out.println("================= regexp result = " + b);
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

    private static void testComparator2() {
        List<SomeObject> someObjects = new ArrayList<>();

        SomeObject someObject1 = new SomeObject();
        someObject1.setName("AA");
//        someObject1.setDate(new Date());
        someObject1.setDate(null);


        SomeObject someObject2 = new SomeObject();
        someObject2.setName("BB");
//        someObject2.setDate(DateUtils.addDays(new Date(), 1));
        someObject2.setDate(null);


        SomeObject someObject3 = new SomeObject();
        someObject3.setName("CC");
        someObject3.setDate(null);

        someObjects.add(someObject1);
        someObjects.add(someObject2);
        someObjects.add(someObject3);

//        SomeObject lastDateObject = Collections.max(someObjects, Comparator.comparing(SomeObject::getDate, Comparator.nullsFirst(Comparator.naturalOrder())));
        SomeObject lastDateObject = someObjects.stream().max(Comparator.comparing(SomeObject::getDate, Comparator.nullsFirst(Comparator.naturalOrder()))).orElse(null);

        int i = 0;
    }

    public static Date getNextWorkDay(Date date) {
        Date nextWorkDay = date;
        nextWorkDay = DateUtils.addDays(nextWorkDay, 1);
        return nextWorkDay;
    }

    private static class SomeObject {
        private List<Integer> list;
        private String name;
        private Date date;

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

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
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
