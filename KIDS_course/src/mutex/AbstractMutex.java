package mutex;

public abstract class AbstractMutex {

    public abstract void lock();
    public abstract void unlock();

    protected int getCurrentThreadId() {
        return Integer.parseInt(Thread.currentThread().getName());
    }
}
