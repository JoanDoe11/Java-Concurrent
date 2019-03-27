package atomic;

import java.awt.*;

public class NaivePair implements ConcurrentPair {

    private int x;
    private int y;


    @Override
    public void writePair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Point readPair() {
        return new Point(x,y);
    }
}
