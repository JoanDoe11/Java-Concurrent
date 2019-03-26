package mutex;

public class IncrementThread implements Runnable {

    private int incrementCount;
    private AbstractMutex mutex;

    public IncrementThread(int incrementCount, AbstractMutex mutex) {
        this.incrementCount = incrementCount;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        for(int i = 0; i<incrementCount; i++){
            mutex.lock();
            Counter.counter++;

            System.out.print("+");

            mutex.unlock();
        }
    }
}
