public class CreateThreadsJava8 {

    static int i = 0;

    public static void main(String[] args) {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                i++;
            }).start();
        }
        System.out.println(i);
    }
}