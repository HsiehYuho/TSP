package bnb;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * Node class for the use of branch and bound expansion, using binary way (select or not select)
 */

public class NodeBinary extends Node{
    private List<int[]> routes;
    private int lowerBound;
    private int[][] matrix;
    private int unvisitNum;
    private int depth;

    public NodeBinary(List<int[]> routes, int lowerBound, int[][] matrix, int unvisitNum, int depth){
        this.routes = routes;
        this.lowerBound = lowerBound;
        this.matrix = matrix;
        this.unvisitNum = unvisitNum;
        this.depth = depth;
    }

    // Generate the next level children nodes
    @Override
    public Node[] genChildren(){
        // Reach the leaf of the tree
        if(unvisitNum == 1){
            return new NodeBinary[0];
        }
        NodeBinary[] children = new NodeBinary[2]; // Only have binary children

        int[][] childMatrix = null;
        List<int[]> childRoutes = new ArrayList<>(this.routes);
        int rowCost = 0;
        int colCost = 0;
        int childCost = 0;

        // Create a child node represent select the lowest edge as next destination
        childMatrix = Util.cloneMatrix(matrix);

        // Reduce
        rowCost = Util.reduce(childMatrix, true);
        colCost = Util.reduce(childMatrix, false);

        int[] fromTo = Util.selectEdge(childMatrix);
        int from = fromTo[0], to = fromTo[1];

        // Dead end
        if(from == -1 || to == -1){
            children[0] = null;
            children[1] = null;
            return children;
        }

        if(rowCost == -1 || colCost == -1 ){
            children[0] = null;
        }
        else{
            childRoutes.add(new int[]{from,to});
            childCost = rowCost + colCost + this.lowerBound; // this.lowerBound is like the overall lowerBound accumulated previously
            Util.updateMatrix(childMatrix, from, to, childRoutes);
            children[0] = new NodeBinary(childRoutes, childCost, childMatrix, this.unvisitNum-1, this.depth + 1);
        }

        // Create another child node to represent not select, reuse the matrix and set
        childMatrix = matrix;
        childMatrix[from][to] = Integer.MAX_VALUE; // Set the edge not picked

        // Reduce
        rowCost = Util.reduce(childMatrix, true);
        colCost = Util.reduce(childMatrix, false);

        if(rowCost == -1 || colCost == -1 ){
            children[1] = null;
        }
        else{
            childCost = rowCost + colCost + this.lowerBound;
            children[1] = new NodeBinary(this.routes, childCost, childMatrix, this.unvisitNum, this.depth + 1);
        }
        return children;
    }

    @Override
    public int getLowerBound(){
        return this.lowerBound;
    }

    @Override
    public List<Integer> getPath(){
        List<Integer> path = new ArrayList<>();

        // Take out the number only appears once, they are the begin and the end of the tour
        int[] count = new int[this.matrix.length];
        for(int[] route : this.routes){
            count[route[0]] += 1;
            count[route[1]] -= 1;
        }
        int[] finalPath = new int[2];
        for(int i = 0; i < count.length; i++){
            if(count[i] == 0) continue;
            if(count[i] == 1) finalPath[1] = i;
            if(count[i] == -1) finalPath[0] = i;
        }
        // Add the final round
        this.routes.add(finalPath);

        int countRoute = 0;
        int ptr = this.routes.get(0)[0];
        while(countRoute < matrix.length){
            for(int[] route : this.routes){
                if(route[0] == ptr){
                    path.add(ptr);
                    ptr = route[1];
                    countRoute++;
                    break;
                }
            }
        }
        path.add(ptr);
        return path;
    }

    @Override
    public int getDepth(){
        return this.depth;
    }

}
