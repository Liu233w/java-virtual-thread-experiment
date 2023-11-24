package com.shuminliu.experiment.java_virtual_thread.generators;

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class CoroutineController<E> {
    private final Semaphore semaphore;
    private final Consumer<E> onYield;
    private final Consumer<E> onTerminate;

    public CoroutineController(Semaphore semaphore, Consumer<E> onYield, Consumer<E> onTerminate) {
        this.semaphore = semaphore;
        this.onYield = onYield;
        this.onTerminate = onTerminate;
    }
    
    public void yield(E e) throws InterruptedException {
        semaphore.acquire();
        semaphore.release();
    }
}
