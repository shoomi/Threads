package creation;

class SomeClass extends Thread {
    @Override
    public void run() {
        System.out.println("The thread with Thread");
    }
}

public class CreatingFromThread {

    public static void main(String[] args) {
        new SomeClass().run();
    }
}
