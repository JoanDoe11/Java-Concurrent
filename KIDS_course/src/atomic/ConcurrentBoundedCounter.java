package atomic;

/**
 * Implement a counter with an upper bound,
 * When the counter reaches the bound, it should reset to 0.
 *
 * It is allowed for the counter to be over the bound for some small limited period of time,
 * as long as it is guaranteed that it will be reset and no increments will be lost.
 */
public interface ConcurrentBoundedCounter {

    void increment();
    int get();
}
