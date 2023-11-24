package com.shuminliu.experiment.java_virtual_thread.generators;

import java.util.function.Consumer;

public class Coroutines {
    public static <I, O> Coroutine<I, O> start(Consumer<CoroutineContext<I, O>> coroutineFunc) {
        var controller = new CoroutineController<>(coroutineFunc);
        return controller.getCoroutine();
    }
}
