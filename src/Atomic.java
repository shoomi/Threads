import java.util.concurrent.atomic.AtomicInteger;

// AtomicInteger може замінити synchronized, якщо логіка стосується лише збільшення змінної
public class Atomic {

    static int i = 0;
    static boolean useAtom = false;
    static AtomicInteger atomInt = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        for (int j = 0; j < 10000; j++) {
            new MyThread().start();
        }
        Thread.sleep(1000);
        System.out.println(useAtom ? "atom i = " + atomInt.get() : "simple i= " + i);

    }

    static class MyThread extends Thread {

        public static synchronized void inc() {
            atomInt.incrementAndGet();
            System.out.println(atomInt.get());
        }

        public static void incSimple() {
            i++;
            System.out.println(i); /// тут різні потоки будуть одночасно виконуватись, тому послідовність виводу буде зажди різна
        }

        public static synchronized void incSimpleSync() {
            i++;
            System.out.println(i); /// тут результат буде такий самий як з AtomicInteger
        }

        @Override
        public void run() {
            if (useAtom) {
                inc();
            } else {
                incSimple();
            }
        }
    }
}
