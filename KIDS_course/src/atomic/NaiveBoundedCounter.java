package atomic;

public class NaiveBoundedCounter implements ConcurrentBoundedCounter{

    private int c;
    private final int LIMIT;

    public NaiveBoundedCounter(int limit){
        this.LIMIT = limit;
    }

    @Override
    public void increment() {

        c++;

        if(c>LIMIT){
            c -= LIMIT;
        }
    }

    @Override
    public int get() {
        return c;
    }
}
