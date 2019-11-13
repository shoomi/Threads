package lock.simpleLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource();
        resource.i = 5;
        resource.j = 5;
        MyTread tread1 = new MyTread();
        tread1.setName("one");
        MyTread thread2 = new MyTread();
        thread2.setName("two");

        tread1.resource = resource;
        thread2.resource = resource;

        tread1.start();
        thread2.start();

        tread1.join();
        thread2.join();
        System.out.println(resource.i);
        System.out.println(resource.j);
    }

    static class MyTread extends Thread {
        Resource resource;

        @Override
        public void run() {
            resource.changeI();
            resource.changeJ();
        }
    }

}


class Resource {
    int i;
    int j;

    private Lock lock = new ReentrantLock();

    // завдяки lock changeJ() виконається після changeI() одним потоком
    // другий потік почекає поки не завершить свою роботу попередній

    void changeI() {
        lock.lock();
        int i = this.i;
        if (Thread.currentThread().getName().equals("one")) {
            Thread.yield();
        }
        i++;
        this.i = i;
        System.out.println("thread "+Thread.currentThread().getName() + " finished changeI()");
    }

    void changeJ() {
        int j = this.j;
        if (Thread.currentThread().getName().equals("one")) {
            Thread.yield();
        }
        j++;
        this.j = j;
        System.out.println("thread "+Thread.currentThread().getName() + " finished changeJ()");
        lock.unlock();
    }


    int count = 0;

    void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}
