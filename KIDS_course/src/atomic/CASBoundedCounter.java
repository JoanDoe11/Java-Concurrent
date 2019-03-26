package atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CASBoundedCounter implements ConcurrentBoundedCounter {

    private AtomicInteger counter = new AtomicInteger(0);
    private final int LIMIT;

    public CASBoundedCounter(int limit){
        this.LIMIT = limit;
    }

    @Override
    public void increment() {

        counter.incrementAndGet();

        if (counter.get() > LIMIT) {
            while(true){
                int tmp = counter.get();

                if(counter.get() < LIMIT || counter.compareAndSet(tmp, tmp - LIMIT)) {
                    break;
                }
            }
        }
    }

    @Override
    public int get() {
        return counter.get();
    }
}
