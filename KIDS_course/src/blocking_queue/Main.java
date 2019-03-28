package blocking_queue;

public class Main {

    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueueImpl(5);

        Thread t1 = new Thread(new ReadingThread(queue));
        Thread t2 = new Thread(new WriterThread(queue));

        t1.setDaemon(true);

        t1.start();
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
