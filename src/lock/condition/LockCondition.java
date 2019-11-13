package lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCondition {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static int account = 0;

    public static void main(String[] args) {
        new AccountMinus().start();
        new AccountPlus().start();
    }

    static class AccountPlus extends Thread {
        @Override
        public void run() {
            lock.lock();
            account += 10;
            condition.signal();
            lock.unlock();
        }
    }

    static class AccountMinus extends Thread {
        @Override
        public void run() {
            if (account < 10) {
                try {
                    lock.lock();
                    System.out.println("account= " + account);
                    condition.await();
                    System.out.println("account= " + account);
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            account -= 10;
        }
    }
}
