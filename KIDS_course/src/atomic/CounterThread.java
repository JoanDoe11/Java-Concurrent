package atomic;

public class CounterThread implements Runnable{

    private static final int COUNT = 10000;
    private ConcurrentBoundedCounter boundedCounter;

    public CounterThread(ConcurrentBoundedCounter boundedCounter){
        this.boundedCounter = boundedCounter;
    }

    @Override
    public void run() {
        for(int i=0; i<COUNT; i++){
            boundedCounter.increment();
        }
    }
}
