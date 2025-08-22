package com.example.testall.httpclient.urlencoded;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientUrlEncodedMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8089/sowa_validate_json"))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("{}")).build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int i = 0;
    }
}
