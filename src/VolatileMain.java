public class VolatileMain {

    private static volatile int i = 0;

    public static void main(String[] args) {

        new MyThreadRead().start();
        new MyThreadWrite().start();
    }

    static class MyThreadWrite extends Thread {
        @Override
        public void run() {
            while (i < 5) {
                System.out.println("increment i to " + (++i));
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyThreadRead extends Thread {
        @Override
        public void run() {
            int localVar = i; // два потоки виконались і мають одинакове занчення i
            while (localVar < 5) {
                // при volatile, поки виконується while у цьому потоці, - інший встигає змінити i
                // без while цей потік дуже швидко буде виконуватись та інший не встигне змінити i
                if (localVar != i) {  // якщо забрати volatile localVer завжди буде 0 бо інший потік не побачить змін, при закоміченому sout
                    System.out.println("new value i is " + i);
                    localVar = i;
                }
            }
        }
    }
}



