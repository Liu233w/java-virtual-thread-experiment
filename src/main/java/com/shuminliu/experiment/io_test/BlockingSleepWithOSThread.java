package com.shuminliu.experiment.io_test;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class BlockingSleepWithOSThread {
    public static void main(String[] args) throws InterruptedException {
        BenchmarkOnExecutor.benchmark(ForkJoinPool.commonPool(), new AnyIORequest(), 100);
    }
}
