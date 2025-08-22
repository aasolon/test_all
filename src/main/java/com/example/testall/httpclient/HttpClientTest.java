package com.example.testall.httpclient;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class HttpClientTest {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8082/hello");
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }

        Thread.sleep(50000);

        httpGet = new HttpGet("http://localhost:8082/hello");
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }
    }
}
