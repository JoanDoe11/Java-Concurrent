package thread_pool.Factorizer;

public class Main {

    private static final int THREAD_NUMBER = 20;
    private static final int LOOP_NUMBER = 10000;
    private static final int MAX_VALUE = 100000;

    public static void main(String[] args) {

        Thread[] threads = new Thread[THREAD_NUMBER];
        Factorizer factorizer = new FactorizerImpl();

        //TODO
    }
}
