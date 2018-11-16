package bnb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * Node class for the use of branch and bound, during expansion, expand multi way
 */

public class NodeMulti extends Node{
    private NodeMulti parent;
    private int idx;
    private int cost;
    private int[][] matrix;
    private Set<Integer> unvisited;

    public NodeMulti(NodeMulti parent, int idx, int cost, int[][] matrix, Set<Integer> unvisited){
        this.parent = parent;
        this.idx = idx;
        this.cost = cost;
        this.matrix = matrix;
        this.unvisited = unvisited;
    }

    // Generate the next level children nodes
    @Override
    public Node[] genChildren(){
        NodeMulti[] children = new NodeMulti[unvisited.size()];

        int i = 0;
        for(int n : unvisited){
            Set<Integer> childSet = new HashSet<>(unvisited);
            childSet.remove(n);
            int[][] childMatrix = Util.cloneMatrix(matrix);


            int rowCost = Util.reduce(childMatrix, true);
            int colCost = Util.reduce(childMatrix, false);
            int childCost = childMatrix[idx][n];

            Util.updateMatrix(childMatrix, idx, n);

            if(rowCost == -1 || colCost == -1){
                return null;
            }

            childCost += rowCost + colCost + this.cost; // this.cost is like the overall cost accumulated previously

            children[i++] = new NodeMulti(this, n, childCost, childMatrix, childSet);
        }

        return children;
    }

    // Return the current cost of certain node
    @Override
    public int getCost(){
        return this.cost;
    }

    // Return the index of current node
    @Override
    public int getIdx(){
        return this.idx;
    }

    // Return the path until current node
    @Override
    public List<Integer> getPath(){
        NodeMulti nodeMulti = this;
        List<Integer> path = new ArrayList<>();
        while(nodeMulti != null){
            path.add(0, nodeMulti.idx);
            nodeMulti = nodeMulti.parent;
        }
        return path;
    }
}
