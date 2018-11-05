package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yu-Ho Hsieh
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Graph class for computing TSP
 *
 */

public class Graph {
    // For input
    private int size;
    private int[][] matrix;

    // For exact result
    private int currentBestCost;
    private List<Integer> currentBestRoutes; // Route <1,5,2,7,6,4>, contains ID sequence

    // For approximation result
    private List<Double> timeStamps;
    private List<Integer> approxCosts;

    public Graph(int size, int[][] matrix){
        this.size = size;
        this.matrix = matrix;
        this.timeStamps = new ArrayList<>();
        this.approxCosts = new ArrayList<>();
    }

    public void setCurrentBestResult(int cost, List<Integer> routes){
        this.currentBestCost = cost;
        this.currentBestRoutes = routes;
    }

    public int[][] getMatrix(){
        return this.matrix;
    }
    public void addApproxResult(int approxCost, double timeStamp){
        this.approxCosts.add(approxCost);
        this.timeStamps.add(timeStamp);
    }

    public int getCurrentBestCost(){
        return this.currentBestCost;
    }

    public List<Integer> getCurrentBestRoutes(){
        return this.currentBestRoutes;
    }

    public List<Integer> getApproxCost(){
        return this.approxCosts;
    }

    public List<Double> getTimeStamp(){
        return this.timeStamps;
    }


}
