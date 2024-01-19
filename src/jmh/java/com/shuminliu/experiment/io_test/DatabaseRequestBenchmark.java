package com.shuminliu.experiment.io_test;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.Executors;

public class DatabaseRequestBenchmark {
    private static final int COUNT = 1000;
    public static final int N_THREADS = 200;

    @Benchmark
    public void testBlockingSleepWithOSThread(Blackhole blackhole) throws InterruptedException {
        try (var executor = Executors.newFixedThreadPool(N_THREADS)) {
            DatabaseRequest.runOnExecutor(executor, COUNT, blackhole::consume);
        }
    }

    @Benchmark
    public void testBlockingSleepWithVirtualThread(Blackhole blackhole) throws InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            DatabaseRequest.runOnExecutor(executor, COUNT, blackhole::consume);
        }
    }
}
