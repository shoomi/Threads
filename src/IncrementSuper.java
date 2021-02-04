import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncrementSuper {

    private static int count;
    private static Lock lock = new ReentrantLock();
    private static Semaphore sem = new Semaphore(4);  // семафор задає ресурс для потоку, але якщо присутні інші потоки яким семафор не заданий то послідовність виконання не гарантується

    private static void runWithSemaphore() {

        IncrementingWithSemaphore in1 = new IncrementingWithSemaphore();
        IncrementingWithSemaphore in2 = new IncrementingWithSemaphore();
        in1.sem = sem;
        in2.sem = sem;
        in1.start();
        in2.start();
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
    }

    private static void runSynchronized() {
        new SynchronizedIncrementing().start();  // синхронізований метод не гарантує послідовність виконання потоків
        new SynchronizedIncrementing().start();
    }

    public static void main(String[] args) throws InterruptedException {
        // краще видно якщо лишити лише один метод для виконання
//        runSimple();
//        runWithJoinSynchronized();
//        runSynchronized();
//        runWithLock();
        runWithSemaphore();
    }

    static class IncrementingWithSemaphore extends Thread {
        Semaphore sem;
        int k = 0;

        @Override
        public void run() {
            try {
                sem.acquire();
                for (int i = 0; i < 20; i++) {
                    count++;
                    k++;
                    System.out.println(currentThread().getName() + " k=" + k + "    count=" + count + "      sem");
                }
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
