public class Test {
    public static void main(String[] args) {
        byte a = 3;
        short b = 4;
        compute(a, b);
    }

    public static void compute(short x, short y) {
        System.out.println("Short: " + (x + y) + (x + y));
    }
}