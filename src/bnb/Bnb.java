package bnb;

import graph.Graph;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Branch and bound computation method
 *
 */

public class Bnb {
    public static Graph compute(Graph g, int cutTime) {
        double curSec = (double)System.currentTimeMillis()/1000.0;
        double stopSec = curSec + cutTime;

        // Set-up cut-off time
        //while(curSec < stopSec){

            // Do the computation
            calculate(g);
            curSec = (double)System.currentTimeMillis()/1000.0;
        //}
        return g;
    }

    // During your computation, please call g.addApproxResult(apporxCost, timeStamp)) once you find a better cost
    // Also, please use g.setCurrentBestResult(int cost, List<Integer> routes) to update the solution

    private static boolean calculate(Graph g){
        int[][] matrix = g.getMatrix();
        for(int[] r : matrix){
            for(int n : r){
                System.out.print(n + " ");
            }
            System.out.println();
        }
        return true;
    }
}