package com.shuminliu.experiment.io_test;

import java.util.List;
import java.util.concurrent.Executors;

public class BlockingIOWithOSThread {
    public static void main(String[] args) throws InterruptedException {

        List<Integer> digits = List.of(3, 4, 5);

        for (Integer digit : digits) {
            long requestCount = ((long) Math.pow(10, digit));
            System.out.println("Request count: " + requestCount);

            BenchmarkOnExecutor.benchmark(Executors.newFixedThreadPool(200), new DatabaseRequest(), requestCount);
        }
    }
}
