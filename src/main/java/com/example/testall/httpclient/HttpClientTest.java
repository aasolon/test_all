package com.example.testall.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

public class HttpClientTest {

    public static void main(String[] args) throws IOException, InterruptedException {
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
