import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncrementSuper {

    private static int count;
    private static final Lock lock = new ReentrantLock();
    private static final Semaphore sem = new Semaphore(1);  // семафор задає ресурс для потоку, але якщо присутні інші потоки яким семафор не заданий то послідовність виконання не гарантується

    public static void main(String[] args) throws InterruptedException {
        // краще видно якщо лишити лише один метод для виконання
//        runSimple();
//        runWithJoinSynchronized();
//        runSynchronized();
//        runWithLock();
        runWithSemaphore();
    }

    private static void runWithSemaphore() {
        new IncrementingWithSemaphore().start();
        new IncrementingWithSemaphore().start();
        new IncrementingWithSemaphore().start();
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
                sem.acquire();
                for (int i = 0; i < 20; i++) {
                    count++;
                    k++;
                    System.out.println(currentThread().getName() + " k=" + k + "    count=" + count + "      sem");
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
