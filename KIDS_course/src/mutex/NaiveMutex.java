package mutex;

/**
 * Naive mutex for two threads
 */

public class NaiveMutex extends AbstractMutex {

    private volatile boolean flags[];

    public NaiveMutex() {
        flags = new boolean[2];
    }

    @Override
    public void lock() {

        //we extract the id before setting the flag, because it can change
        int threadId = getCurrentThreadId();
        flags[threadId] = true;

        //loop until the other thread finishes
        while(flags[1-threadId] == true){
            ;
        }

    }

    @Override
    public void unlock() {

        //release the critical section
        flags[getCurrentThreadId()] = false;
    }
}
