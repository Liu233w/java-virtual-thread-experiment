package com.shuminliu.experiment.io_test;

import java.util.concurrent.Executors;

public class BlockingSleepWithVirtualThread {
    public static void main(String[] args) throws InterruptedException {
        BenchmarkOnExecutor.benchmark(Executors.newVirtualThreadPerTaskExecutor(), new AnyIORequest(), 10000);
    }
}
