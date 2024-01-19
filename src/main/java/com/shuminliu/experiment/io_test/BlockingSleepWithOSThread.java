package com.shuminliu.experiment.io_test;

import java.util.concurrent.Executors;

public class BlockingSleepWithOSThread {
    public static void main(String[] args) throws InterruptedException {
        BenchmarkOnExecutor.benchmark(Executors.newFixedThreadPool(200), new MockBlockingRequest(), 1000);
    }
}
