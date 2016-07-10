package multithreading.java8.tutorial2.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static multithreading.java8.common.Utils.sleep;
import static multithreading.java8.common.Utils.teardown;

/**
 * Created by sbiyyala on 7/10/16.
 */
public class RentrantLockUsage {

    ExecutorService executor = Executors.newFixedThreadPool(2);
    ReentrantLock lock = new ReentrantLock();
    int count = 0;

    void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String... args) {
        RentrantLockUsage lu = new RentrantLockUsage();
        lu.sample1();
    }

    private void sample1() {
        executor.submit(() -> {
            lock.lock();
            try {
                sleep(1);
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        teardown(executor);
    }
}

