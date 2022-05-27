package com.example.testall.url;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlTest {

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        String urlStr = "http://localhost/get?param1=value1";
        String path1 = new URL(urlStr).getPath();
        System.out.println(path1);
        String path2 = new URI(urlStr).getPath();
        System.out.println(path2);
    }
}
