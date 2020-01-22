package notify.not1;

public class Notifier implements Runnable {

    private Message msg;

    public Notifier(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("thread '" + name + "' has started");
        try {
            Thread.sleep(1000);
            synchronized (msg) {
                msg.setMsg("This is a message from '" + name + "' thread. Hello waiter! You can start your job!");
                msg.notify();
//                 msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
