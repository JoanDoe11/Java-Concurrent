package mutex;

/**
 * This is an exercise with the goal of understanding how mutex works.
 *
 * @author  JoanDoe11
 * @version 1.0
 * @since   2019-03-21
 */

public class Main {

    private static final int COUNT = 10000;
    private static final int THREAD_COUNT = 10;

    private static void twoThreadMutexTest() {
        //AbstractMutex mutex = new NaiveMutex();
        AbstractMutex mutex = new PetersonMutex();

        Thread t1 = new Thread(new IncrementThread(COUNT, mutex), "0");
        Thread t2 = new Thread(new DecrementThread(COUNT, mutex),"1");

        t1.start();
        t2.start();

        try {
            System.out.println("started");
            t1.join();
            t2.join();

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("count="+Counter.counter);
    }

    private static void nThreadMutexTest(){
        AbstractMutex mutex = new FilterMutex(THREAD_COUNT);

        Thread[] threads = new Thread[THREAD_COUNT];

        for(int i=0; i<THREAD_COUNT; i++){
            if(i%2 == 0){
                threads[i] = new Thread(new IncrementThread(COUNT,mutex), String.valueOf(i));
            }
            else {
                threads[i] = new Thread(new DecrementThread(COUNT,mutex),String.valueOf(i));
            }
        }

        for(Thread t: threads){
            t.start();
        }

        for(Thread t:threads){

            try{
                t.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("count="+Counter.counter);
    }

    public static void main(String[] args){

        //twoThreadMutexTest();
        nThreadMutexTest();

    }
}
