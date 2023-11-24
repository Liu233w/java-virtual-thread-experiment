package com.shuminliu.experiment.java_virtual_thread;

import com.shuminliu.experiment.java_virtual_thread.generators.Coroutines;

import java.util.Optional;

public class CoroutineTest {
    public static void main(String[] args) {
        var coroutine = Coroutines.<String, String>start(context -> {
            System.out.println("before yield. Thread id: " + Thread.currentThread().threadId() + ", Thread name: " + Thread.currentThread().getName());
            Optional<String> input = context.yield("hello");
            System.out.println("after yield. Input: " + input.get() + ", Thread id: " + Thread.currentThread().threadId() + ", Thread name: " + Thread.currentThread().getName());
        });

        System.out.println("before resume. Thread id: " + Thread.currentThread().threadId() + ", Thread name: " + Thread.currentThread().getName());
        Optional<String> output1 = coroutine.resume("input");
        System.out.println("after first resume. Output: " + output1.get() + ", Thread id: " + Thread.currentThread().threadId() + ", Thread name: " + Thread.currentThread().getName());
        Optional<String> output2 = coroutine.resume();
        System.out.println("after second resume. Output: " + output2.get() + ", Thread id: " + Thread.currentThread().threadId() + ", Thread name: " + Thread.currentThread().getName());
    }
}
