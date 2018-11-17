package ls1;

import utils.InitialRouteUtils;
import graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Roddick
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Local search method 1
 *
 */

public class Ls1 {
   public static Graph compute(Graph g, int cutTime, int seed)
    {

        int[][] test_m=g.getMatrix();
        int cityCount = g.getMatrix().length;
      InitialRouteUtils.createInitialRandomRoute(g, 0);

        List<Integer> currentRoute = g.getCurrentBestRoutes();
        int currentCost;
        currentRoute = g.getCurrentBestRoutes();
        currentCost=g.getCurrentBestCost();

        //System.out.println(currentRoute);
        //System.out.println(currentCost);
        //List<Integer> alternateRoute = Get_result_threeOpt(g, 4, 8, 11);

        //System.out.println(alternateRoute);


            for(int i=1; i < cityCount -2; i++ )
            {

                for(int j=i+1; j < cityCount -1; j++ )
                {
                    for(int k =j+1; k< cityCount; k++)
                    {
                        List<Integer> alternateRoute = Get_result_threeOpt(g, i, j, k);
                        int alternateCost = Cost_calculator(g, alternateRoute);
                        if(alternateCost < currentCost)
                        {

                            g.setCurrentBestResult(alternateCost, alternateRoute);
                            currentCost = g.getCurrentBestCost();
                        }
                    }
                }

            }

        System.out.println("Best cost " + g.getCurrentBestCost());
        System.out.println("Route: "+g.getCurrentBestRoutes());
        System.out.println("Percentage off optimal: "+((g.getCurrentBestCost()-893536)/(893536.00))*100+"%");

        return g;
    }

    public static int Cost_calculator(Graph g, List<Integer> route)
    {
        int cost = 0;
        int[][] graphMatrix = g.getMatrix();
        for(int i=0; i<route.size()-1; i++)
        {
            cost = cost + graphMatrix[route.get(i)][route.get(i+1)];
        }
        return cost;
    }


    public static List<Integer> Get_result_threeOpt(Graph g, int edge1, int edge2, int edge3) {
        List<Integer> Current_route = new ArrayList<Integer>();
        Current_route=g.getCurrentBestRoutes();
        List<Integer> sub_route = new ArrayList<Integer>();
        List<Integer> sub_route_head = new ArrayList<Integer>();
        List<Integer> sub_route_second = new ArrayList<Integer>();
        List<Integer> sub_route_third = new ArrayList<Integer>();
        List<Integer> sub_route_fourth = new ArrayList<Integer>();
        int Best_cost = g.getCurrentBestCost();
        //System.out.print("Best_cost");
        //System.out.println(Best_cost);
        int sub_cost = 0;

        //Initialize head of subroute
        for (int i = edge3; i < Current_route.size(); ++i)
            sub_route_fourth.add(Current_route.get(i));
        for (int i = 0; i < edge1; ++i)
            sub_route_head.add(Current_route.get(i));

        //System.out.print("H");
        //System.out.println(sub_route_head);
        //Initialize second_part of subroute
        for (int i = edge1; i < edge2; ++i)
            sub_route_second.add(Current_route.get(i));

        //System.out.print("S");
        //System.out.println(sub_route_second);
        //Initialize third_part of subroute
        for (int i = edge2; i < edge3; ++i)
            sub_route_third.add(Current_route.get(i));
        //System.out.print("T");
        //System.out.println(sub_route_third);
        //System.out.print("Cur:");
        //System.out.println(Current_route);
        //Combination(1.) : ACB
        sub_route.addAll(sub_route_head);
        sub_route.addAll(sub_route_third);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost) {
            Best_cost = sub_cost;
            Current_route.clear();
            Current_route.addAll(sub_route);
        }
        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);
        sub_route.clear();

        //Combination(2.) : ACB^{-1}
        sub_route.addAll(sub_route_head);
        sub_route.addAll(sub_route_third);
        Collections.reverse(sub_route_second);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost)
          {
            Best_cost = sub_cost;
            Current_route.clear();
            Current_route.addAll(sub_route);
          }
        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);
        sub_route.clear();
        //Combination(3.) : AB^{-1}C
        sub_route.addAll(sub_route_head);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_third);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost) {
            Best_cost = sub_cost;
            Current_route.clear();
            Current_route.addAll(sub_route);
        }
        Collections.reverse(sub_route_second);
        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);
        sub_route.clear();

        //Combination(4.) : A C^{-1} B
        sub_route.addAll(sub_route_head);
        Collections.reverse(sub_route_third);
        sub_route.addAll(sub_route_third);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost) {
            Best_cost = sub_cost;
            Current_route.clear();
            Current_route.addAll(sub_route);
        }
        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);
        sub_route.clear();

        //Combination(5.) : A B C^{-1}
        sub_route.addAll(sub_route_head);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_third);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost)
        {
            Best_cost = sub_cost;
            Current_route.clear();
            Current_route.addAll(sub_route);

        }
        Collections.reverse(sub_route_second);
        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);
        sub_route.clear();
        //System.out.println(Current_route);


        //Combination(6.) : AC^{-1}B^{-1}
        sub_route.addAll(sub_route_head);
        sub_route.addAll(sub_route_third);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost)
          {

              Best_cost = sub_cost;
              Current_route.clear();
              Current_route.addAll(sub_route);
          }

        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);
        sub_route.clear();
        //Combination(7.) : AB^{-1}C^{-1}
        sub_route.addAll(sub_route_head);
        sub_route.addAll(sub_route_second);
        sub_route.addAll(sub_route_third);
        sub_route.addAll(sub_route_fourth);
        sub_cost = Cost_calculator(g, sub_route);
        if (sub_cost < Best_cost)
          {
              Best_cost = sub_cost;
              Current_route.clear();
              Current_route.addAll(sub_route);
          }
        //System.out.print("Cost:");
        //System.out.println(sub_cost);
        //System.out.print("sub:");
        //System.out.println(sub_route);

        return Current_route;
    }

}
