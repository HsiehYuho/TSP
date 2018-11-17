package utils;

import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitialRouteUtils {
    public static Graph createInitialRandomRoute(Graph g, int seed){
        int cityCount = g.getMatrix().length;
        int[][] cityMatrix = g.getMatrix();
        ArrayList<Integer> currentRoute = new ArrayList<Integer>();
        int cost;
        Random rn = new Random(seed);
        currentRoute.add(0);
        while(currentRoute.size() < cityCount){
            int city = rn.nextInt(cityCount - 1) + 1;
            if(!currentRoute.contains(city)) {
                currentRoute.add(city);
            }
        }
        currentRoute.add(0);

        cost = GraphUtils.calculateCost(g, currentRoute);
        g.setCurrentBestResult(cost, currentRoute);
        return g;
    }

    public static Graph createInitialRouteDFS(Graph g) {
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
            cost = cost + cityMatrix[currentCity][closestCity];
            currentRoute.add(closestCity);
            routeMatrix[currentCity][closestCity] = cityMatrix[currentCity][closestCity];
            currentCity = closestCity;
            visitedCount++;
        }
        g.setCurrentBestResult(cost, currentRoute);
        return g;
    }
}
