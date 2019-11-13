package ciclycbarier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CiclicBarierExample {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Run());
        new Sportsman(cyclicBarrier);
        new Sportsman(cyclicBarrier);
//      new Sportsman(cyclicBarrier);
    }

    // потік Run не запуститься доки не запустяться три потоки Sportsman

    static class Run extends Thread {
        @Override
        public void run() {
            System.out.println("Run is begun");
        }
    }

    static class Sportsman extends Thread {
        CyclicBarrier barrier;

        public Sportsman(CyclicBarrier barrier) {
            this.barrier = barrier;
            start();
        }

        @Override
        public void run() {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }
}
