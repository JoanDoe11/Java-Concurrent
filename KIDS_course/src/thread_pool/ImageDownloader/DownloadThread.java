package thread_pool.ImageDownloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadThread implements Callable<Integer> {

    private static AtomicInteger imageCounter = new AtomicInteger(0);

    @Override
    public Integer call() throws Exception {
        BufferedImage img = null;

        img = ImageIO.read(new URL("https://picsum.photos/200/300/?random"));

        int myNumber = imageCounter.getAndIncrement();
        ImageIO.write(img,"png", new File("img/"+myNumber+".png"));

        return myNumber;
    }
}
