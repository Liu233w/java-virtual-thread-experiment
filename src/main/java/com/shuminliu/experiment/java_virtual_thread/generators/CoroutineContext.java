package com.shuminliu.experiment.java_virtual_thread.generators;

import java.util.Optional;

public interface CoroutineContext<I, O> {
    Optional<I> yield(O output);
    Optional<I> yield();
}
