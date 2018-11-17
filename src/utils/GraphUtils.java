package utils;

import graph.Graph;

import java.util.List;

public class GraphUtils {
    public static int calculateCost(Graph g, List<Integer> route){
        int cost = 0;
        int[][] graphMatrix = g.getMatrix();
        for(int i=0; i<route.size()-1; i++){
            cost = cost + graphMatrix[route.get(i)][route.get(i+1)];
        }
        return cost;
    }
}
