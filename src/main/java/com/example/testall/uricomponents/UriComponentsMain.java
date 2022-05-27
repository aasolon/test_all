package com.example.testall.uricomponents;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriComponentsMain {

//    public static final String VALUE_1 = "Привет как дела!";
//    public static final String VALUE_1 = "Hi how are you!";
    public static final String VALUE_1 = "AAAAAA";

    public static void main(String[] args) {
        String url1 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).toUriString();
        System.out.println("\n.toUriString()\n" + url1);

        String url2 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build().toUriString();
        System.out.println("\n.build().toUriString()\n" + url2);

        String url3 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build().encode().toUriString();
        System.out.println("\n.build().encode().toUriString()\n" + url3);

        try {
            String url4 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build(true).encode().toUriString();
            System.out.println("\n.build(true).encode().toUriString()\n" + url4);
        } catch (Exception e) {
            System.out.println("\n.build(true).encode().toUriString()\n" + e);
        }

        try {
            String url5 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build(true).toUriString();
            System.out.println("\n.build(true).toUriString()\n" + url5);
        } catch (Exception e) {
            System.out.println("\n.build(true).toUriString()\n" + e);
        }

        URI url6 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build().toUri();
        System.out.println("\n.build().toUri()\n" + url6);

        URI url7 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build().encode().toUri();
        System.out.println("\n.build().encode().toUri()\n" + url7);

        try {
            URI url8 = UriComponentsBuilder.fromPath("/get").queryParam("param1", VALUE_1).build(true).toUri();
            System.out.println("\n.build(true).toUri()\n" + url8);
        } catch (Exception e) {
            System.out.println("\n.build(true).toUri()\n" + e);
        }

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://httpbin.org" + url2, String.class);
        System.out.println(response);
    }
}
