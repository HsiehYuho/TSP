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
}
