package atomic;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CASConcurrentPair implements ConcurrentPair{

    private int xArray[];
    private int yArray[];

    private AtomicInteger writePointer = new AtomicInteger(0);
    private volatile int readPointer;

    public CASConcurrentPair(int maxWrites){
        xArray = new int[maxWrites];
        yArray = new int[maxWrites];
    }


    @Override
    public void writePair(int x, int y) {
        int myWritePosition = writePointer.getAndIncrement();

        xArray[myWritePosition] = x;
        yArray[myWritePosition] = y;

        readPointer = myWritePosition;
    }

    @Override
    public Point readPair() {
        int myReadPosition = readPointer;
        return new Point(xArray[myReadPosition],yArray[myReadPosition]);
    }
}
