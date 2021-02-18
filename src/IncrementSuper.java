import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class IncrementSuper {

    private static int count;
    private static final Lock lock = new ReentrantLock();
    private static final int permits = 2;
    private static Semaphore sem = new Semaphore(permits);  // семафор задає ресурс для потоку, але якщо присутні інші потоки яким семафор не заданий то послідовність виконання не гарантується
    private static volatile AtomicInteger thredNum = new AtomicInteger(0);
    private static volatile AtomicInteger prevNum = new AtomicInteger(1);
    private static volatile AtomicInteger countSem = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // краще видно якщо лишити лише один метод для виконання
//        runSimple();
//        runWithJoinSynchronized();
//        runSynchronized();
//        runWithLock();
        runWithSemaphore();
    }

    private static void runWithSemaphore() {
        IntStream.range(1,15).forEach(i -> {
            new IncrementingWithSemaphore().start();
        });
    }

    private static void runSimple() {
        new SimpleIncrementing().start();
        new SimpleIncrementing().start();
    }

    private static void runWithJoinSynchronized() throws InterruptedException {
        SimpleIncrementing simpleIncrementing = new SimpleIncrementing();
        simpleIncrementing.start();
        simpleIncrementing.join();
        new SimpleIncrementing().start();
    }

    private static void runWithLock() {
        new IncrementingWithLock().start(); // лок гарантує що поки один потік не закінчить роботу з методом - інший цей метод рухати не буде
        new IncrementingWithLock().start();
        new IncrementingWithLock().start();
    }

    private static void runSynchronized() {
        new SynchronizedIncrementing().start();  // синхронізований метод не гарантує послідовність виконання потоків
        new SynchronizedIncrementing().start();
    }

    static class IncrementingWithSemaphore extends Thread {
        int k = 0;

        @Override
        public void run() {
            try {
                synchronized (IncrementingWithSemaphore.class) {
                    if (thredNum.incrementAndGet() > prevNum.get() && permits >1) {
                        prevNum.set(thredNum.get());
                            Thread.sleep(1500);
                    }
                }
                sem.acquire();
                for (int i = 0; i < 1; i++) {
                    countSem.incrementAndGet();
                    Thread.sleep(4000);
                    System.out.println(currentThread().getName() + "    count=" + countSem.get() + "      sem");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                sem.release();
            }
        }
    }

    static class SimpleIncrementing extends Thread {
        int k = 0;

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                count++;
                k++;
                System.out.println(currentThread().getName() + " k=" + k + "    count=" + count + "      simple");
            }
        }

    }

    static class IncrementingWithLock extends Thread {
        int k = 0;

        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 20; i++) {
                    count++;
                    k++;
                    System.out.println(currentThread().getName() + " k=" + k + "    count=" + count + "      lock");
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class SynchronizedIncrementing extends Thread {
        int k = 0;
        int z = 0;

        @Override
        public synchronized void run() {
            for (int i = 0; i < 20; i++) {
                z = count++;
                k++;
                System.out.println(currentThread().getName() + " k=" + k + "    count=" + count + "      sync");
            }
        }
    }


}
