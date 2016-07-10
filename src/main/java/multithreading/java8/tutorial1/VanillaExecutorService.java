package multithreading.java8.tutorial1;

import java.util.concurrent.*;

import static multithreading.java8.common.Utils.teardown;

/**
 * Created by shishir.biyyala on 7/9/16.
 * Playground class to refresh concepts
 */
public class VanillaExecutorService {

    public static void main(String... args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = createRunnableTask();
        Callable<Integer> callable = createCallableTask();
        Future runnableFuture = executor.submit(task);
        //spinlock(runnableFuture);
        Future<Integer> callableFuture = executor.submit(callable);

        // unconditional get() call blocks => equivalent of spinlock
        try {
            Integer callableResult = callableFuture.get(1, TimeUnit.SECONDS);
            runnableFuture.get();
            System.out.println(callableResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("One of tasks timed out!");
            e.printStackTrace();
        }

        System.out.println(runnableFuture.isDone());
        System.out.println(callableFuture.isDone());
        teardown(executor);
    }

    /**
     * Creates and returns a callable task
     * @return
     */
    private static Callable<Integer> createCallableTask() {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 10000;
            } catch (InterruptedException e) {
                throw new IllegalStateException("Task interrupted", e);
            }
        };

        return task;
    }

    /**
     * Creates and returns a runnable instance
     * @return
     */
    private static Runnable createRunnableTask() {
        Runnable task = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Foo " + threadName);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + threadName);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        return task;
    }

}
