package lock.tryLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLock {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new MyTread().start();
        new MyTread2().start();
    }

    static class MyTread extends Thread {

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(getName() + " start working");
                sleep(50);
                System.out.println(getName() + " stop working");
                lock.unlock();
                System.out.println(getName() + " thread has been unlocked");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyTread2 extends Thread {

        @Override
        public void run() {
            System.out.println(getName() + " begin working");
            while (true) {
                // тут перевіряється чи знятий lock()
                if (lock.tryLock()) {
                    System.out.println(getName() + " working");
                    break;
                } else {
                    System.out.println(getName() + " waiting");
                }
            }
        }
    }

}
