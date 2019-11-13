package semaphore;

import java.util.concurrent.Semaphore;

public class Semaphr {

    // семафор використовує лічильник для доступу до ресурсів. коли запускається новий лічильник зменшується на одиницю,
    // після завершення роботи потоком, - лічильник збільшується на одиницю
    // якщо лічильник 0 - потік блокуєтсья та чекає поки інший потік не завершить роботу

    public static void main(String[] args) {
        Semaphore table = new Semaphore(2);

        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
        Person person4 = new Person();

        person1.table = table;
    }

}

class Person extends Thread {
    Semaphore table;

    @Override
    public void run() {
        System.out.println(this.getName() + " waiting for table");
        try {
            table.acquire();
            System.out.println(this.getName() + " eat at the table");
            System.out.println(this.getName() + " release table");
            table.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
