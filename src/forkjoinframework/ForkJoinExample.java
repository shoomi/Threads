package forkjoinframework;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {

    // операція рахування виконується вдвічі швидше ніж звичайним способом

    private static long numberOfOperations = 10_000_000_000L;
    private static int numberOfThreads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.now();
        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);
        System.out.println(pool.invoke(new MyFork(0, numberOfOperations)));

        System.out.println(Duration.between(startTime, LocalDateTime.now()).getSeconds());
    }

    static class MyFork extends RecursiveTask<Long> {

        long from, to;

        public MyFork(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if ((to - from) <= numberOfOperations / numberOfThreads) {  // якщо операція розбита на потрібну к-сть частин
                long j = 0;
                for (long i = from; i < to; i++) {
                    j += i;
                }
                return j;
            } else {                                           // якщо не розбита - розбиваєм на потрібну к-сть
                long middle = (to + from) / 2;
                MyFork firsHalf = new MyFork(from, middle);
                firsHalf.fork();
                MyFork secondHalf = new MyFork(middle + 1, to);
                long secondValue = secondHalf.compute();
                return firsHalf.join() + secondValue;
            }
        }
    }
}
