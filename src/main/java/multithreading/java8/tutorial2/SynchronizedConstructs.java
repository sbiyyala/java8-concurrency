package multithreading.java8.tutorial2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static multithreading.java8.common.Utils.teardown;

/**
 * Created by sbiyyala on 7/10/16.
 */
public class SynchronizedConstructs {

    static int count = 0;

    public static void main(String... args) {

        sample1();
    }

    private static void sample1() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(SynchronizedConstructs::incrementSync));

        teardown(executor);

        System.out.println(count);
    }

    synchronized static void incrementSync() {
        count += 1;
    }

}
