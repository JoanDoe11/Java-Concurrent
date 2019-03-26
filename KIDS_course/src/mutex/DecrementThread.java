package mutex;

public class DecrementThread implements Runnable{

    private int decrementCount;
    private AbstractMutex mutex;

    public DecrementThread(int decrementCount, AbstractMutex mutex) {
        this.decrementCount = decrementCount;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        for(int i=0; i<decrementCount; i++){
            mutex.lock();

            Counter.counter--;

            System.out.print("-");

            mutex.unlock();
        }
    }
}
