package blocking_queue;

public class WriterThread implements Runnable{

    private BlockingQueue queue;

    public WriterThread(BlockingQueue queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        for(int i=0; i<10000; i++){
            queue.enqueue(i);
            System.out.println("Writing:"+i);
        }
    }
}
