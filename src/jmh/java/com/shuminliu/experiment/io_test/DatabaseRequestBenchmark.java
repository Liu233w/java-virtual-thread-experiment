package com.shuminliu.experiment.io_test;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Executors;

public class DatabaseRequestBenchmark {
    private static final int COUNT = 1000;
    public static final int N_THREADS = 200;

    @Benchmark
    public void testBlockingSleepWithOSThread(Blackhole blackhole) throws InterruptedException {
        DatabaseRequest.runOnExecutor(Executors.newFixedThreadPool(N_THREADS), COUNT, blackhole);
    }

    @Benchmark
    public void testBlockingSleepWithVirtualThread(Blackhole blackhole) throws InterruptedException {
        DatabaseRequest.runOnExecutor(Executors.newVirtualThreadPerTaskExecutor(), COUNT, blackhole);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DatabaseRequestBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
