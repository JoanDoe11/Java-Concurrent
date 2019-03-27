package atomic;

import java.awt.*;

/**
 * The goal is to make accessing a pair of variables together possible concurrently.
 */

public interface ConcurrentPair {

    void writePair(int x, int y);
    Point readPair();
    //we use Point just for ease, because it has attributes x and y
}
