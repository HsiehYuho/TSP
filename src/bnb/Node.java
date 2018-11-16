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
    private Node parent;
    private int idx;
    private int cost;
    private int[][] matrix;
    private Set<Integer> unvisited;

//    public Node(Node parent, int idx, int cost, int[][] matrix, Set<Integer> unvisited){
//        this.parent = parent;
//        this.idx = idx;
//        this.cost = cost;
//        this.matrix = matrix;
//        this.unvisited = unvisited;
//    }


    public abstract Node[] genChildren();
    public abstract int getCost();
    public abstract int getIdx();
    public abstract List<Integer> getPath();

}
