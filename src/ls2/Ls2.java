package ls2;

import graph.Graph;
import utils.GraphUtils;
import utils.InitialRouteUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Asra
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Local search method 2 : 2-opt Exchange
 *
 */

public class Ls2 {
    public static Graph compute(Graph g, int cutTime, int seed ) {
        int cityCount = g.getMatrix().length;
        InitialRouteUtils.createInitialRandomRoute(g, seed);

        int currentCost;
        int difference=0;
        int flag = 0;
        long startTime = System.currentTimeMillis();
        while((System.currentTimeMillis() - startTime)/1000 < cutTime){
            currentCost = g.getCurrentBestCost();
            for(int j=1; j < cityCount -1; j++ ){
                for(int k =j+1; k< cityCount; k++){
                    List<Integer> alternateRoute = twoOptSwap(g, j, k);
                    int alternateCost = GraphUtils.calculateCost(g, alternateRoute);
                    if(alternateCost < currentCost){
                        difference=0;
                        g.setCurrentBestResult(alternateCost, alternateRoute);
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1){
                    flag = 0;
                    break;
                }
            }
            difference++;
        }
        System.out.println("Proposed Route: " + g.getCurrentBestRoutes());
        System.out.println("Cost: "+ g.getCurrentBestCost());
        return g;
    }
    public static  List<Integer> twoOptSwap(Graph g, int j, int k){
        List<Integer> route = g.getCurrentBestRoutes();
        List<Integer> alternateRoute = new ArrayList<Integer>();
        for(int i=0; i<j; i++){
            alternateRoute.add(route.get(i));
        }
        for(int i=k; i>=j; i--){
            alternateRoute.add(route.get(i));
        }
        for(int i=k+1; i<route.size(); i++){
            alternateRoute.add(route.get(i));
        }
        return alternateRoute;
    }
}