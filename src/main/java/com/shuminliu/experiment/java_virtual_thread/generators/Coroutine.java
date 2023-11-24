package com.shuminliu.experiment.java_virtual_thread.generators;

import java.util.Optional;

public interface Coroutine<I, O> {
    Optional<O> resume(I input);
    Optional<O> resume();
}
