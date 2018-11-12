package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
    private PriorityQueue<Edge> edgeList;

    // For exact result
    private int currentBestCost;
    private List<Integer> currentBestRoutes; // Route <1,5,2,7,6,4>, contains ID sequence

    // For approximation result
    private List<Double> timeStamps;
    private List<Integer> approxCosts;

    public Graph(int size, int[][] matrix, PriorityQueue<Edge> edgeList){
        this.size = size;
        this.matrix = matrix;
        this.edgeList = edgeList;
        this.timeStamps = new ArrayList<>();
        this.approxCosts = new ArrayList<>();
    }

    public void setCurrentBestResult(int cost, List<Integer> routes){
        this.currentBestCost = cost;
        this.currentBestRoutes = routes;
    }

    public int getSize() {
    	return this.size;
    }
    
    public int[][] getMatrix(){
        return this.matrix;
    }
    
    public PriorityQueue<Edge> getEdgeList(){
    	return this.edgeList;
    }
    
    public void printEdges() {
    	PriorityQueue<Edge> copy = edgeList;
    	Edge cur = copy.poll();
    	while (cur != null) {
    		System.out.println(cur.toString());
    		cur = copy.poll();
    	}
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
