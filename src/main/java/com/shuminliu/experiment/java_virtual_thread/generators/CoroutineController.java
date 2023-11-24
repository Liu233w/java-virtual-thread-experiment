package com.shuminliu.experiment.java_virtual_thread.generators;

import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;

public class CoroutineController<I, O> {
    private final CyclicBarrier barrier = new CyclicBarrier(2);
    private final CoroutineContext<I, O> context;
    @lombok.Getter
    private final Coroutine<I, O> coroutine;
    private final Thread thread;
    private Optional<I> coroutineInput;
    private Optional<O> coroutineOutput;
    private CoroutineState coroutineState = CoroutineState.RUNNING;

    @SneakyThrows
    public CoroutineController(Consumer<CoroutineContext<I, O>> coroutineFunc) {
        coroutineInput = Optional.empty();
        coroutineOutput = Optional.empty();
        context = new ContextImpl();
        coroutine = new CoroutineImpl();

        thread = Thread.ofVirtual().start(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                coroutineFunc.accept(context);
                coroutineState = CoroutineState.DONE;
                barrier.await();
            }
        });
        barrier.await();
    }

    private class ContextImpl implements CoroutineContext<I, O> {

        @lombok.SneakyThrows
        public Optional<I> yield(O output) {
            coroutineOutput = Optional.of(output);
            barrier.await();
            barrier.await();
            return coroutineInput;
        }

        @lombok.SneakyThrows
        public Optional<I> yield() {
            coroutineOutput = Optional.empty();
            barrier.await();
            barrier.await();
            return coroutineInput;
        }
    }

    private class CoroutineImpl implements Coroutine<I, O> {
        @lombok.SneakyThrows
        public Optional<O> resume(I input) {
            assertCoroutineNotDone();
            coroutineInput = Optional.of(input);
            barrier.await();
            barrier.await();
            return coroutineOutput;
        }

        @lombok.SneakyThrows
        public Optional<O> resume() {
            assertCoroutineNotDone();
            coroutineInput = Optional.empty();
            barrier.await();
            barrier.await();
            return coroutineOutput;
        }

        private void assertCoroutineNotDone() {
            if (coroutineState == CoroutineState.DONE) {
                throw new IllegalStateException("Coroutine has been done.");
            }
        }
    }

    private static enum CoroutineState {
        RUNNING,
        DONE,
    }
}
