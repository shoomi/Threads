package pool;

import java.util.concurrent.*;

public class ThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // тут реально створюється лише два потоки
        for (int i = 0; i < 10; i++) {
            executorService.submit(new MyRunnabe());
            System.out.println(executorService.submit(new MyCallable()).get());
        }
        executorService.shutdown();
    }

    static class MyRunnabe implements Runnable {
        @Override
        public void run() {
            System.out.println(1);
        }
    }

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "2";
        }
    }
}
