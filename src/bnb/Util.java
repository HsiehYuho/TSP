package bnb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Util packages
 */

public class Util {
    /**
     * Get the init known best cost, (so that we can easily prune tree)
     *
     * @param matrix the distance among
     * @param routes The best current path
     *
     * @return cost found by adding up closest cities
     */

    public static int findGreedyCost(int[][] matrix, List<Integer> routes){
        routes.add(0);
        int cost = 0;
        Set<Integer> remains = new HashSet<>();
        int size = matrix.length;

        for(int i = 1; i < size; i++){
            remains.add(i);
        }

        int curCity = 0;
        while(remains.size() != 0){
            int nearestCity = remains.iterator().next();
            int nearestCost = matrix[curCity][nearestCity];
            for(int nextCity : remains){
                int nextCost = matrix[curCity][nextCity];
                if(nearestCost > nextCity){
                    nearestCity = nextCity;
                    nearestCost = nextCost;
                }
            }
            cost += nearestCost;
            routes.add(nearestCity);
            curCity = nearestCity;
            remains.remove(nearestCity);
        }
        // Visit last city and go back to original city
        cost += matrix[curCity][0];
        routes.add(0);
        return cost;
    }

    public static int reduce(int[][] matrix, boolean isRow){
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

    public static int[][] cloneMatrix(int[][] matrix){
        int[][] matrixCopy = new int[matrix.length][];
        for(int i = 0; i < matrix.length; i++){
            matrixCopy [i] = matrix[i].clone();
        }
        return matrixCopy;
    }

    // Update the corresponding cols and rows in matrix to infinite
    public static void updateMatrix(int[][] matrix, int sourceCity, int targetCity){
        int size = matrix.length;
        for(int i = 0; i < size; i++){
            matrix[sourceCity][i] = Integer.MAX_VALUE;
            matrix[i][targetCity] = Integer.MAX_VALUE;
        }

        // Avoid leaving unvisited cities, from(having nodes from 1~ 4, 1->2->3->1, assign 3->1 = MAX_VALUE)
        matrix[targetCity][0] = Integer.MAX_VALUE;
    }

    // Debug
    public static void printMatrix(int[][] matrix){
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
