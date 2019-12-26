package lock.tryLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLock {

    private static Lock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        new MyTread().start();
        MyTread2 myTread2 = new MyTread2();
        myTread2.start();
    }

    static class MyTread extends Thread {

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(getName() + "The main task has start working");
                sleep(50);
                System.out.println(getName() + "The main task has stop working");
                lock.unlock();
                System.out.println(getName() + "The main thread has been unlocked");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyTread2 extends Thread {

        @Override
        public void run() {
            while (true) {
                // тут перевіряється чи знятий lock()
                if (lock.tryLock()) {
                    System.out.println(getName() + " : I see the main task has been finished");
                    break;
                } else {
                    System.out.println(getName() + " waiting for ending of the main task");
                }
            }
        }
    }

}
