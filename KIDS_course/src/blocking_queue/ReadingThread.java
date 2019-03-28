package blocking_queue;

public class ReadingThread implements Runnable {

    private BlockingQueue queue;

    public ReadingThread(BlockingQueue queue){
        this.queue = queue;
    }


    @Override
    public void run() {
        while(true) {
            int i = queue.dequeue();
            System.out.println("Reading:"+i);
        }
    }
}
