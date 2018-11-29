package approx;

import graph.Graph;
import graph.AdjacencyList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * @author Courtney
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Approximated computation using MST approx
 *
 */

public class Approx {
    public static Graph compute(Graph g, int cutTime, int seed) {
    	long t0 = System.nanoTime();
    	
    	// Create Minimum Spanning Tree
    	AdjacencyList<Integer> mst = MST.compute_MST(g, seed);
//    	mst.print();
    	
    	// Randomized Depth First Search
    	Stack<Integer> lifo = new Stack<>();
    	int[] visited = new int[g.getSize()]; // initialize all 0
    	Random rand = new Random(seed);
    	
    	int cur = rand.nextInt(g.getSize()); // start at a random node
    	lifo.push(cur);
    	LinkedList<Integer> neighbors = new LinkedList<>();
    	List<Integer> route = new ArrayList<>();
    	
    	while (!lifo.isEmpty()) {
    		cur = lifo.pop();
    		visited[cur] = 1;
    		route.add(cur);
    		neighbors = mst.getEdgeList(cur);
    		for (int ii = 0; ii < neighbors.size(); ii++) {
    			if (visited[neighbors.get(ii)] == 0) {
    				lifo.push(neighbors.get(ii));
    			}
    		}
    	}
    	route.add(route.get(0)); // close the circuit by returning to the initial node
    	
    	int cost = 0;
    	for (int ii = 1; ii < route.size(); ii++) {
    		cost+= g.getMatrix()[route.get(ii-1)][route.get(ii)];
    	}
    	
    	long runtime = (System.nanoTime()-t0)/1000000000;
    	if (runtime <= cutTime) {
        	g.addApproxResult(cost, runtime); // seconds
    	}
    	
//    	System.out.println(String.format("Cost: %d", cost));
//    	System.out.println(route);
    	
    	g.setCurrentBestResult(cost, route);
    	
//    	// Obtain subset of odd-degree nodes
//        List<Integer> odds = new ArrayList<>(g.getSize()); // capacity is n
//        for (int ii = 0; ii < g.getSize(); ii++) {
//        	if (mst.getEdgeList(ii).size() % 2 == 1) {
//        		odds.add(ii);
//        	}
//        }
////        System.out.println(odds.toString());
//        
//        // Obtain minimum-weight perfect matching using the Hungarian algorithm
//        int[][] mat = new int[g.getSize()][g.getSize()];
//        for (int ii = 0; ii < odds.size(); ii++) {
//        	for (int jj = 0; jj < odds.size(); jj++) {
//        		mat[odds.get(ii)][odds.get(jj)] = 
//        	}
//        }
    	
    	return g;
    }
    
}
