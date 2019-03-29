package thread_pool.ImageDownloader;

import java.util.concurrent.*;

/**
 * NOT WORKING
 */

public class DownloadService {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> service = new ExecutorCompletionService<>(threadPool);

        for(int i=0; i<1; i++){
            Future<Integer> job = service.submit(new DownloadThread());
        }

        for(int i=0; i<100; i++){
            try{
                Future<Integer> result = service.take();

                int resultMumber = result.get();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        threadPool.shutdown();
    }
}
