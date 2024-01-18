package com.shuminliu.experiment.io_test;

import java.util.concurrent.ExecutorService;

public class BenchmarkOnExecutor {
    public static void benchmark(ExecutorService threadPool, Runnable runnable, long count) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            threadPool.submit(runnable);
        }

        threadPool.shutdown();

        long endTime = System.currentTimeMillis();
        long durationMs = (endTime - startTime);
        double throughput = count * 1.0 / durationMs;
        System.out.println("Throughput: " + throughput + " operations per ms");
    }
}
