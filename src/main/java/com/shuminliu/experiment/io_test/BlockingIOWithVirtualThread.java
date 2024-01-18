package com.shuminliu.experiment.io_test;

import java.util.concurrent.Executors;
import java.util.List;

public class BlockingIOWithVirtualThread {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> digits = List.of(5, 10, 15);

        for (Integer digit : digits) {
            long requestCount = ((long) Math.pow(10, digit));
            System.out.println("Request count: " + requestCount);

            BenchmarkOnExecutor.benchmark(Executors.newVirtualThreadPerTaskExecutor(), new DatabaseRequest(), requestCount);
        }
    }
}
