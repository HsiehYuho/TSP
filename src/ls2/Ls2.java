package ls2;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Asra
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Local search method 2
 *
 */

public class Ls2 {
    public static Graph compute(Graph g, int cutTime, int seed ) {
        int cityCount = g.getMatrix().length;
        createInitialRoute(g);
        int currentCost;
        int difference=0;
        int flag = 0;
        while(difference < 100){
            currentCost = g.getCurrentBestCost();
            for(int j=1; j < cityCount -1; j++ ){
                for(int k =j+1; k< cityCount; k++){
                    List<Integer> alternateRoute = twoOptSwap(g, j, k);
                    int alternateCost = calculateCost(g, alternateRoute);
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
        System.out.println(g.getCurrentBestCost());
        System.out.println(g.getCurrentBestRoutes());

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
    public static Graph createInitialRoute(Graph g) {
        int cityCount = g.getMatrix().length;
        int[][] routeMatrix = new int[cityCount][cityCount];
        List<Integer> currentRoute = new ArrayList<Integer>();
        int[][] cityMatrix = g.getMatrix();

        int currentCity = 0;
        int visitedCount = 0;
        currentRoute.add(currentCity);
        int cost = 0;
        while (visitedCount < cityCount) {
            int closestCity = currentCity;
            for (int i = 0; i < cityCount; i++) {
                if (currentCity != i && !currentRoute.contains(i) && cityMatrix[currentCity][i] < cityMatrix[currentCity][closestCity]) {
                    closestCity = i;
                }
            }
            if (currentCity == closestCity) {
                closestCity = 0;
            }
            //System.out.println(currentCity + "\t" + closestCity + "\t" + cityMatrix[currentCity][closestCity]);
            cost = cost + cityMatrix[currentCity][closestCity];
            currentRoute.add(closestCity);
            routeMatrix[currentCity][closestCity] = cityMatrix[currentCity][closestCity];
            currentCity = closestCity;
            visitedCount++;
        }
        System.out.println(cost);
        g.setCurrentBestResult(cost, currentRoute);
       /*
        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                System.out.print(routeMatrix[i][j] + "\t");
            }
            System.out.println();
        }
        */
        return g;
    }
    public static int calculateCost(Graph g, List<Integer> route){
        int cost = 0;
        int[][] graphMatrix = g.getMatrix();
        for(int i=0; i<route.size()-1; i++){
            cost = cost + graphMatrix[route.get(i)][route.get(i+1)];
        }
        return cost;
    }
}