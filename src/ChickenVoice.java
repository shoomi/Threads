import java.util.stream.IntStream;

class EggVoice extends Thread {

    @Override
    public void run() {
        IntStream.range(1, 5).forEach((n) -> System.out.println("яйцо"));
    }
}

public class ChickenVoice {

    public static void main(String[] args) {
        //Побочный поток
        EggVoice mAnotherOpinion = new EggVoice();
        System.out.println("Спор начат...");
        mAnotherOpinion.start();            //Запуск потока

        IntStream.range(1, 5).forEach((n) -> System.out.println("курица"));  // тут запускається з головного потоку

//Если оппонент еще не сказал последнее слово
        if (mAnotherOpinion.isAlive()) {
            try {
                mAnotherOpinion.join();
            } catch (InterruptedException ignored) {  //Подождать пока оппонент закончит высказываться.
            }
            System.out.println("Первым появилось яйцо!");
        } else    //если оппонент уже закончил высказываться
        {
            System.out.println("Первой появилась курица!");
        }
        System.out.println("Спор закончен!");
    }
}