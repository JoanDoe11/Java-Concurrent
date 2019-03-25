package mutex;

/**
 * The algorithm for two threads uses two variables, flags and victim.
 * A flags[n] value of true indicates that the process n wants to enter the critical section.
 * Entrance to the critical section is granted for process P0 if P1 doesn't want to enter its
 * critical section or if P1 has given priority to P0 by setting victim to 0.
 *
 */

public class PetersonMutex extends AbstractMutex {

    private volatile boolean flags[];
    private volatile int victim;

    public PetersonMutex() {
        flags = new boolean[2];
    }

    @Override
    public void lock() {
        int threadId = getCurrentThreadId();
        flags[threadId] = true;
        victim = threadId;

        while(flags[1-threadId]==true && victim == 1-threadId){
            ;
        }
    }

    @Override
    public void unlock() {
        flags[getCurrentThreadId()] = false;
    }
}
