package atomic;

public class Main {

    private static final int THREAD_COUNT = 10;
    private static final int LIMIT = 3000;

    public static void main(String[] args) {
        ConcurrentBoundedCounter counter = new NaiveBoundedCounter(LIMIT);
        //ConcurrentBoundedCounter counter = new CASBoundedCounter(LIMIT);
        Thread[] threads = new Thread[THREAD_COUNT];

        for(int i=0; i<THREAD_COUNT; i++){
            threads[i] = new Thread(new CounterThread(counter));
        }

        for(Thread t:threads){
            t.start();
        }

        for(Thread t:threads){
            try {
                t.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println(counter.get());
    }
}
