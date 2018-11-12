package bnb;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * Node class, to represnet the traverse tree
 */

public class Node {
    private Node parent;
    private int idx;
    private int cost;
    private int[][] matrix;
    private Set<Integer> unvisited;

    public Node(Node parent, int idx, int cost, int[][] matrix, Set<Integer> unvisited){
        this.parent = parent;
        this.idx = idx;
        this.cost = cost;
        this.matrix = matrix;
        this.unvisited = unvisited;
    }

    // Generate the next level children nodes
    public Node[] genChildren(){
        Node[] children = new Node[unvisited.size()];

        int i = 0;
        for(int n : unvisited){
            Set<Integer> childSet = new HashSet<>(unvisited);
            childSet.remove(n);
            int[][] childMatrix = cloneMatrix(matrix);


            int rowCost = reduce(childMatrix, true);
            int colCost = reduce(childMatrix, false);
            int childCost = childMatrix[idx][n];

            updateMatrix(childMatrix, idx, n);


            if(rowCost == -1 || colCost == -1){
                return null;
            }

            childCost += rowCost + colCost + this.cost; // this.cost is like the overall cost accumulated previously

            children[i++] = new Node(this, n, childCost, childMatrix, childSet);
        }

        return children;
    }

    // Return the current cost of certain node
    public int getCost(){
        return this.cost;
    }

    // Return the index of current node
    public int getIdx(){
        return this.idx;
    }

    // Return the path until current node
    public List<Integer> getPath(){
        Node node = this;
        List<Integer> path = new ArrayList<>();
        while(node != null){
            path.add(0,node.idx);
            node = node.parent;
        }
        return path;
    }

    private int[][] cloneMatrix(int[][] matrix){
        int[][] matrixCopy = new int[matrix.length][];
        for(int i = 0; i < matrix.length; i++){
            matrixCopy [i] = matrix[i].clone();
        }
        return matrixCopy;
    }

    // Update the corresponding cols and rows in matrix to infinite
    private void updateMatrix(int[][] matrix, int sourceCity, int targetCity){
        int size = matrix.length;
        for(int i = 0; i < size; i++){
            matrix[sourceCity][i] = Integer.MAX_VALUE;
            matrix[i][targetCity] = Integer.MAX_VALUE;
        }

        // Avoid leaving unvisited cities, from(having nodes from 1~ 4, 1->2->3->1, assign 3->1 = MAX_VALUE)
        matrix[targetCity][0] = Integer.MAX_VALUE;
    }

    private int reduce(int[][] matrix, boolean isRow){
        int cost = 0;
        int size = matrix.length;

        for(int i = 0; i < size; i++){
            int min = Integer.MAX_VALUE;

            for(int j = 0; j < size; j++){

                int num = isRow ? matrix[i][j] : matrix[j][i];
                if(num == 0){
                    min = 0;
                    break; // has already find minimum
                }
                if(num < min){
                    min = num;
                }
            }

            // minus cost from matrix row by row / col by col
            if(min == 0 || min == Integer.MAX_VALUE){
                continue; // no need to minus cost from matrix
            }

            for(int j = 0; j < size; j++){
                if(isRow){
                    matrix[i][j] = matrix[i][j] == Integer.MAX_VALUE ? matrix[i][j] : matrix[i][j] - min;
                }
                else{
                    matrix[j][i] = matrix[j][i] == Integer.MAX_VALUE ? matrix[j][i] : matrix[j][i] - min;
                }
            }

            // sum the minimum cost
            cost += min;
        }

        return cost;
    }

    private void printMatrix(int[][] matrix){
        for(int[] row : matrix){
            for(int n : row){
                if(n == Integer.MAX_VALUE){
                    System.out.print(-1 + "\t");
                }
                else{
                    System.out.print(n + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
