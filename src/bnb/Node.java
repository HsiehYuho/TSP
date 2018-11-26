package bnb;

import java.util.List;
import java.util.Set;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * Node abstract class for the use of branch and bound
 */


public abstract class Node {
    public abstract Node[] genChildren();
    public abstract int getLowerBound();
    public abstract List<Integer> getPath();
    public abstract int getDepth();
}
