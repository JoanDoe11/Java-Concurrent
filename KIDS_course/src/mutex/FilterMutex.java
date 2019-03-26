package mutex;

/**
 * The filter algorithm generalizes Peterson's algorithm to N>2 processes.
 * Instead of a boolean flag, it requires an integer variable per process, stored in a single writer/multiple reader atomic register,
 * and N-1 additional variables in similar registers.
 *
 * The level variables take on values up to N-1, each representing a distinct "waiting room" before the critical section.
 * Processes advance from one room to the next, finishing in room N-1 which is the critical section.
 * To acquire a lock, process i marks level[i] to be the first next level, and victim[that level]=i.
 * To release the lock upon exiting the critical section, process i sets level[i] to -1.
 *
 */

public class FilterMutex extends AbstractMutex {

    private volatile int levels[];
    private volatile int victims[];
    private int threadCount;

    public FilterMutex(int threadCount) {
        levels = new int[threadCount];
        victims = new int[threadCount];

        for(int i=0; i<threadCount;i++){
            levels[i] = -1;
        }

        this.threadCount = threadCount;
    }

    @Override
    public void lock() {

        int currentThreadId = getCurrentThreadId();

        for(int l=0; l<threadCount; l++){
            levels[currentThreadId] = l;
            victims[l] = currentThreadId;

            boolean loop = true;
            boolean existsOther;
            while(loop){
                existsOther = false;

                //check if any threads are on higher levels
                for(int i = 0; i<threadCount; i++){
                    if(i==currentThreadId){
                        continue;
                    }
                    if(levels[i]>=levels[currentThreadId]){
                        existsOther = true;
                        break;
                    }
                }

                if(existsOther&&victims[l]==currentThreadId){
                    loop = true;
                }
                else {
                    loop = false;
                }
            }
        }
    }

    @Override
    public void unlock() {

        levels[getCurrentThreadId()] = -1;
    }
}
