package com.example.testall.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientTest {

    public static void main(String[] args) throws InterruptedException {
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(60 * 1000)
                                .setConnectionRequestTimeout(30 * 1000)
                                .setSocketTimeout(60 * 1000)
                                .build()
                )
                .setMaxConnPerRoute(2)
                .setMaxConnTotal(2)
//                .setConnectionTimeToLive(5, TimeUnit.SECONDS) // время жизни соединения в пуле, контролируемое исключительно на стороне клиента
//                .evictIdleConnections(5, TimeUnit.SECONDS) // если соединение простаивало какое-то время (пусть даже и не закрыто сервером), то оно будет закрыто
//                .evictExpiredConnections() // если соединение было разорвано на стороне сервера, то оно будет закрыто (при этом время по умолчанию 10 сек или же берется из evictIdleConnections),
                                           // при этом простаивающие соединения не афектятся
                // PoolingHttpClientConnectionManager.setValidateAfterInactivity раньше перед выдачей соединения из пула оно всегда проверялось,
                                                                              // а теперь оно проверяется только если прошло больше указанного кол-ва  ms
                .build();

        HttpGet get1 = new HttpGet("http://localhost:8081/hello");
        HttpGet get2 = new HttpGet("http://localhost:8081/hello2");
        HttpGet get3 = new HttpGet("http://localhost:8081/hello3");

        MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client, get1);
        MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client, get2);
        MultiHttpClientConnThread thread3 = new MultiHttpClientConnThread(client, get3);
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    public static class MultiHttpClientConnThread extends Thread {
        private CloseableHttpClient client;
        private HttpGet get;

        public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get) {
            this.client = client;
            this.get = get;
        }

        // standard constructors
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " prepare make request");
                HttpResponse response = client.execute(get);
                System.out.println(Thread.currentThread().getName() + " made request");
                Thread.sleep( 20 * 1000);
                System.out.println(Thread.currentThread().getName() + " wake up");
                EntityUtils.consume(response.getEntity());
            } catch (IOException | InterruptedException ex) {
            }
        }
    }
}
