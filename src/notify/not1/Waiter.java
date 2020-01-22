package notify.not1;

public class Waiter implements Runnable {

    private Message msg;
    private String name;

    public Waiter(Message m) {
        this.msg = m;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (msg) {
            try {
                System.out.println("thread '" + name + "' is waiting for new work. But notify me!");
                msg.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ось цей код спрацює лише коли визиветься notify!!!
            System.out.println("thread:'" + name + "' - notify was made. I'm starting");
            // обработаем наше сообщение
            System.out.println(name + " : " + msg.getMsg());
        }
    }
}
