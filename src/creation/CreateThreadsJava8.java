package creation;

public class CreateThreadsJava8 {

    static int i = 0;

    static void createThreadsUsingThread() {
        Runnable runnable = () -> {
            i++;
        };
        for (int j = 0; j < 10; j++) {
            new Thread(runnable).start();
            System.out.println(i);
        }
    }

    static void createThreadsUsingRunnable() {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                i++;
                System.out.println(i);
            }).start();
        }
    }

    public static void main(String[] args) {
        createThreadsUsingRunnable();
        createThreadsUsingThread();

    }
}
