package com.example.testall.httpclient;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpClientMultiThreadTest {

    public static void main(String[] args) throws InterruptedException {
        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(60, TimeUnit.SECONDS)
                .setSocketTimeout(60, TimeUnit.SECONDS)
//                .setTimeToLive(5, TimeUnit.SECONDS) // время жизни соединения в пуле, контролируемое исключительно на стороне клиента
//                .setValidateAfterInactivity(5, TimeUnit.SECONDS) // раньше перед выдачей соединения из пула оно всегда проверялось,
                // а теперь оно проверяется только если прошло больше указанного кол-ва  ms
                .build();
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connConfig)
                .setMaxConnPerRoute(2)
                .setMaxConnTotal(2)
                .build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(30 * 1000))
                .build();

        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
//                .evictIdleConnections(TimeValue.ofSeconds(5)) // если соединение простаивало какое-то время (пусть даже и не закрыто сервером), то оно будет закрыто
//                .evictExpiredConnections() // если соединение было разорвано на стороне сервера, то оно будет закрыто (при этом время по умолчанию 10 сек или же берется из evictIdleConnections),
//                                            при этом простаивающие соединения не афектятся
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
                CloseableHttpResponse response = client.execute(get);
                System.out.println(Thread.currentThread().getName() + " made request");
                Thread.sleep( 20 * 1000);
                System.out.println(Thread.currentThread().getName() + " wake up");
                EntityUtils.consume(response.getEntity());
            } catch (IOException | InterruptedException ex) {
            }
        }
    }
}
