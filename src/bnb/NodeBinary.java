package bnb;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * Node class for the use of branch and bound, during expansion, expand binary way (select or not select)
 */

public class NodeBinary extends Node{
    private NodeBinary parent;
    private int idx;
    private int cost;
    private int[][] matrix;
    private Set<Integer> unvisited;

    public NodeBinary(NodeBinary parent, int idx, int cost, int[][] matrix, Set<Integer> unvisited){
        this.parent = parent;
        this.idx = idx;
        this.cost = cost;
        this.matrix = matrix;
        this.unvisited = unvisited;
    }

    // Generate the next level children nodes
    @Override
    public Node[] genChildren(){
        // Reach the leaf of the tree
        if(unvisited.size() == 0){
            return new NodeBinary[0];
        }
        NodeBinary[] children = new NodeBinary[2]; // Only have binary children

        int[][] childMatrix = null;
        Set<Integer> childSet = null;
        int rowCost = 0;
        int colCost = 0;
        int childCost = 0;

        // Create a child node represent select the lowest edge as next destination
        childMatrix = Util.cloneMatrix(matrix);

        // Reduce
        rowCost = Util.reduce(childMatrix, true);
        colCost = Util.reduce(childMatrix, false);

        // Dead end
        if(rowCost == -1 || colCost == -1){
            return null;
        }

        childSet = new HashSet<>(unvisited);
        int minDesIdx = -1;
        int minCost = Integer.MAX_VALUE;
        for(int des : childSet){
            int cost = childMatrix[this.idx][des];
            minDesIdx = minCost > cost ? des : minDesIdx;
            minCost = Math.min(minCost,cost);
        }
        // Dead end
        if(minDesIdx == -1){
            return null;
        }
        childSet.remove(minDesIdx);
        childCost = childMatrix[this.idx][minDesIdx];
        childCost += rowCost + colCost + this.cost; // this.cost is like the overall cost accumulated previously
        Util.updateMatrix(childMatrix, this.idx, minDesIdx);
        children[0] = new NodeBinary(this, minDesIdx, childCost, childMatrix, childSet);

        // Create another child node to represent not select
        childMatrix = Util.cloneMatrix(matrix);
        childSet = new HashSet<>(unvisited);
        childMatrix[this.idx][minDesIdx] = Integer.MAX_VALUE; // Set the edge not picked

        // Reduce
        rowCost = Util.reduce(childMatrix, true);
        colCost = Util.reduce(childMatrix, false);

        int minSecCost = Integer.MAX_VALUE;
        for(int i = 0; i < childMatrix.length; i++){
            minSecCost = minSecCost > childMatrix[this.idx][i] ?  childMatrix[this.idx][i] : minSecCost;
        }
        childCost = rowCost + colCost + this.cost;

        children[1] = new NodeBinary(this, idx, childCost, childMatrix, childSet);

        return children;
    }

    @Override
    public int getCost(){
        return this.cost;
    }

    @Override
    public List<Integer> getPath(){
        NodeBinary nodeBinary = this;
        List<Integer> path = new ArrayList<>();
        while(nodeBinary != null){
            if(path.size()== 0 || path.get(0) != nodeBinary.getIdx()){
                path.add(0, nodeBinary.getIdx());
            }
            nodeBinary = nodeBinary.parent;
        }
        return path;

    }

    @Override
    public int getIdx(){
        return this.idx;
    }

}
