package atomic;

import java.awt.*;

public class Main {

    private static final int THREAD_COUNT = 30;
    private static final int LIMIT = 3000;
    private static final int WRITE_COUNT = 10000;



    public static void main(String[] args) {

        pairTest();


    }

    private static void count(){
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

    private static void pairTest(){
        //ConcurrentPair pair = new NaivePair();
        ConcurrentPair pair = new CASConcurrentPair(WRITE_COUNT*THREAD_COUNT/2);

        Thread[] threads = new Thread[THREAD_COUNT];


        for (int i =0; i<THREAD_COUNT; i++){
            if(i%2 == 0){
                //writer threads
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<WRITE_COUNT; i+=2){
                            pair.writePair(i,i+1);
                        }
                    }
                });
            } else {
                //reader threads
                threads[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            Point p = pair.readPair();
                            if(p.x != p.y-1){
                                System.out.println("Error");
                            }
                        }
                    }
                });
                threads[i].setDaemon(true);
            }
        }

        for(Thread t:threads){
            t.start();
        }
    }
}
