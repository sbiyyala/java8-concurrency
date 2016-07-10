package multithreading.java8.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by sbiyyala on 7/10/16.
 * A collection of static utility methods
 */
public class Utils {

    /**
     * Shuts down the executor service gracefully
     * @param executor
     */
    public static void teardown(ExecutorService executor) {

        try {
            System.out.println("Attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }

            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    /**
     * For testing
     * @param f
     */
    private static void spinlock(Future f) {
        while (!f.isDone()) {
        }
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
