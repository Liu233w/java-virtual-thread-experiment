package com.shuminliu.experiment.io_test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class ReactorMockBlockingRequest {

    public static void run(int count) throws InterruptedException {
        Flux.range(1, count)
            .publishOn(Schedulers.parallel())
            .flatMap(
                i -> Mono.just(i).delayElement(Duration.ofSeconds(1)),
                Integer.MAX_VALUE
            )
            .blockLast();
    }
}
