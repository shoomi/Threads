package creation;

class SomeThing implements Runnable {
    @Override
    public void run() {
        System.out.println("New thread with Runnable");
    }
}

public class CreatingFromRunnable {

    public static void main(String[] args) {
        new Thread(new SomeThing()).run();
    }
}
