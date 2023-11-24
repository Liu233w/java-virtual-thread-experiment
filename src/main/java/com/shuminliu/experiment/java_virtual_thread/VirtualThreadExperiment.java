package com.shuminliu.experiment.java_virtual_thread;

public class VirtualThreadExperiment {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.ofVirtual().start(() -> {
            System.out.println("Hello, virtual thread!");
        });

        thread.join();
    }
}
