package com.example.testall.uricomponents;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

public class UriComponentsMain2 {

    public static void main(String[] args) {
        String s = UriComponentsBuilder
                .fromPath("/v1/{externalId}/state/{rusName}/p1")
                .buildAndExpand(UUID.randomUUID(), "фывфыв")
                .toUriString();

        System.out.println(s);
    }
}
