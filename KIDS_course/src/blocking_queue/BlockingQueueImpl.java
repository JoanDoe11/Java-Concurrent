package blocking_queue;

import java.util.ArrayList;

public class BlockingQueueImpl implements BlockingQueue {

    private ArrayList<Integer> data = new ArrayList<>();

    private final int LIMIT;

    public BlockingQueueImpl(int limit){
        this.LIMIT = limit;
    }

    @Override
    public void enqueue(Integer i) {

        synchronized (this) {

            //queue is full
            while (data.size() == LIMIT){
                try {
                    wait();  // block because the queue is full
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            //queue isn't full
            data.add(i);
            notifyAll();    //wake up readers
        }
    }

    @Override
    public Integer dequeue() {

        Integer result = null;

        synchronized (this) {

            // empty queue
            while(data.isEmpty()) {
                try {
                    wait(); //wait for writers
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            // non-empty queue
            result = data.get(0);
            data.remove(0);

            notifyAll();    //wake up writers
        }

        return result;
    }
}
