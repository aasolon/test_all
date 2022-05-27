package com.example.testall.multithreadrequests;

import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class MultiThreadRequestsMain {

    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 100; i++) {
            MultiThreadRequestsMain.MultiRestTemplateThread newThread = new MultiThreadRequestsMain.MultiRestTemplateThread(restTemplate);
            newThread.start();
//            thread1.join();
        }

//        MultiThreadRequestsMain.MultiRestTemplateThread thread2 = new MultiThreadRequestsMain.MultiRestTemplateThread(restTemplate);
//        MultiThreadRequestsMain.MultiRestTemplateThread thread3 = new MultiThreadRequestsMain.MultiRestTemplateThread(restTemplate);
//
//        thread2.start();
//        thread3.start();
//
//        thread2.join();
//        thread3.join();

        Thread.sleep(30 * 1000);
    }

    public static class MultiRestTemplateThread extends Thread {
        private final RestTemplate restTemplate;

        public MultiRestTemplateThread(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        // standard constructors
        public void run() {
            String result = restTemplate.getForObject("http://localhost:8082/tweets-non-blocking?requestId="+ UUID.randomUUID(), String.class);
            System.out.println(Thread.currentThread() + " " + result);
        }
    }

    private class Car {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
