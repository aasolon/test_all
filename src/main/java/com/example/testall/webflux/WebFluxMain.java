package com.example.testall.webflux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class WebFluxMain {

    public static void main(String[] args) throws InterruptedException {        Mono.just(1)
                .map(e -> log(e, "map1"))
                .doOnSuccess(e -> registerRequest())
                .then(Mono.just(2))
                .subscribe(e -> log(e, "subscribe"));

//        Flux.range(1, 5)
//                .map(e -> log(e, "map1"))
//                .doOnComplete(() -> log("AAA", "doOnComplete"))
//                .subscribe(e -> log(e, "subscribe"));

        Thread.sleep(5000);
    }

    private static <T> T log(T el, String function) {
        System.out.println(Thread.currentThread().getName() + ": element " + el + " [" + function + "]");
        return el;
    }

    private static void registerRequest() {
        Mono.fromRunnable(() -> log(null, "doOnSuccess"))
                .subscribeOn(Schedulers.boundedElastic()).subscribe();
    }
}
