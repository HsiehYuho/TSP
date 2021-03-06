package ls1;

import utils.GraphUtils;
import utils.InitialRouteUtils;
import graph.Graph;

import java.util.*;
import java.time.*;

/**
 * @author Roddick
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Local search method 1
 *
 */


public class Ls1 {

    public static int Mode=0;
    public static Graph compute(Graph g, int cutTime, int seed)
    {
        // For test git push

        int count=0;
        int cityCount=g.getMatrix().length;
        int position_one=0;
        int position_two=0;
        int Best_cost= Integer.MAX_VALUE;
        int Current_cost= 0;
        List<Integer> Best_route=new ArrayList<>();
        Random rand = new Random();
        int reset_seed=seed;
        long startTime = System.currentTimeMillis();
        long duration=0;
        //while(count<100)
        while((System.currentTimeMillis()-startTime)/1000<cutTime)
        {

            //System.out.print("Mode "+Mode);
            Test_3_opt(g,cutTime,reset_seed);
            Mode=1;
            if(Best_cost>g.getCurrentBestCost())
            {
              Best_cost=g.getCurrentBestCost();
              Best_route=new ArrayList<>(g.getCurrentBestRoutes());
            }

            if(Best_cost==Current_cost) {
                reset_seed = rand.nextInt();
                Mode=0;
            }

            Current_cost=Best_cost;
            //System.out.println(" BC "+Best_cost); //+ "BR:" + Best_route);
            count++;

        }




        long endTime = (System.currentTimeMillis()-startTime)/1000;
        //System.out.println(endTime);

        g.setCurrentBestResult(Best_cost,Best_route);
        return g;


    }
    public static Graph Test_3_opt(Graph g, int cutTime ,int seed) {
        int[][] Matrix = g.getMatrix();
        int cityCount = g.getMatrix().length;

        List<Integer> Best_Route = new ArrayList();



        int BestCost=0;

        if(Mode==0) {
            Best_Route = createRandomRoute(g, seed);
            BestCost =Best_Route.get(cityCount+1);
            Best_Route.remove(cityCount+1);
            Mode++;
        }
        else
        {
            Best_Route=g.getCurrentBestRoutes();
            BestCost=g.getCurrentBestCost();
        }

        int Current_cost=BestCost;


/*

            Best_Route = createRandomRoute(g, seed);

            int BestCost =Best_Route.get(cityCount+1);//keep being updated in for loop
            int Current_cost=BestCost;
            Best_Route.remove(cityCount+1);

*/

        long startTime = System.currentTimeMillis();
        //int count = 0;
        //int flag = 0;


        for (int i = 1; i < cityCount - 2; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > cutTime) {
                break;
            }
            for (int j = i + 1; j < cityCount - 1; j++) {
                if ((System.currentTimeMillis() - startTime) / 1000 > cutTime) {
                    break;
                }
                for (int k = j + 1; k < cityCount; k++) {
                    if ((System.currentTimeMillis() - startTime) / 1000 > cutTime) {
                        break;
                    }


                    List<Integer> alternateRoute = ThreeOpt(g, Best_Route, Current_cost,BestCost, i, j, k);

                    if (!(alternateRoute.isEmpty())) {
                        BestCost = alternateRoute.get(cityCount + 1);
                        alternateRoute.remove(cityCount + 1);
                        Best_Route.clear();
                        Best_Route = new ArrayList(alternateRoute);



                    }


                }

            }

        }




        g.setCurrentBestResult(BestCost,Best_Route);
        return g;


    }





    public static List<Integer> createRandomRoute(Graph g, int seed){
        int cityCount = g.getMatrix().length;
        //int[][] cityMatrix = g.getMatrix();
        ArrayList<Integer> currentRoute = new ArrayList<Integer>();
        int cost;
        int starting_point=0;
        int rnd;
        Random rd_num = new Random(seed);
        HashSet rndset = new HashSet<Integer> (cityCount);
        for(int i=0;i<cityCount;++i)
        {
            rnd=(int) (rd_num.nextInt(cityCount));
            if(i==0)
            {
                starting_point=rnd;
            }
            while(!rndset.add(rnd))
            {
                rnd = (int) (rd_num.nextInt(cityCount));

            }
            currentRoute.add(rnd);
        }


        currentRoute.add(starting_point);

        cost = GraphUtils.calculateCost(g, currentRoute);
        currentRoute.add(cost);

        return currentRoute;
    }


    public static List<Integer> Best_combination(List<Integer> Current_route,int index,int edge1, int edge2, int edge3)
    {

        List<Integer> Best_route=new ArrayList<>();
        List<Integer> sub_route_head = new ArrayList<Integer>();
        List<Integer> sub_route_second = new ArrayList<Integer>();
        List<Integer> sub_route_third = new ArrayList<Integer>();
        List<Integer> sub_route_fourth = new ArrayList<Integer>();

        //D
        for (int i = edge3; i < Current_route.size(); ++i)
            sub_route_fourth.add(Current_route.get(i));

        //A
        for (int i = 0; i < edge1; ++i)
            sub_route_head.add(Current_route.get(i));
        //B
        for (int i = edge1; i < edge2; ++i)
            sub_route_second.add(Current_route.get(i));

        //C
        for (int i = edge2; i < edge3; ++i)
            sub_route_third.add(Current_route.get(i));

        switch (index)
        {
            case 0://ABCD
                return Current_route;
            case 1://ACBD
                Best_route.addAll(sub_route_head);
                Best_route.addAll(sub_route_third);
                Best_route.addAll(sub_route_second);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            case 2://ACB^{-1}D
                Best_route.addAll(sub_route_head);
                Best_route.addAll(sub_route_third);
                Collections.reverse(sub_route_second);
                Best_route.addAll(sub_route_second);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            case 3://AB^{-1}CD
                Best_route.addAll(sub_route_head);
                Collections.reverse(sub_route_second);
                Best_route.addAll(sub_route_second);
                Best_route.addAll(sub_route_third);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            case 4://AC^{-1}BD
                Best_route.addAll(sub_route_head);
                Collections.reverse(sub_route_third);
                Best_route.addAll(sub_route_third);
                Best_route.addAll(sub_route_second);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            case 5://ABC^{-1}D
                Best_route.addAll(sub_route_head);
                Best_route.addAll(sub_route_second);
                Collections.reverse(sub_route_third);
                Best_route.addAll(sub_route_third);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            case 6://AC^{-1}B^{-1}D
                Best_route.addAll(sub_route_head);
                Collections.reverse(sub_route_third);
                Best_route.addAll(sub_route_third);
                Collections.reverse(sub_route_second);
                Best_route.addAll(sub_route_second);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            case 7://AB^{-1}C^{-1}D
                Best_route.addAll(sub_route_head);
                Collections.reverse(sub_route_second);
                Best_route.addAll(sub_route_second);
                Collections.reverse(sub_route_third);
                Best_route.addAll(sub_route_third);
                Best_route.addAll(sub_route_fourth);
                return Best_route;
            default:
                System.out.println("Error:Unkonw index");
                return Best_route;

        }


    }



    public static List<Integer> ThreeOpt(Graph g,List<Integer> Initial_route ,int Current_cost,int overall_best_cost ,int edge1, int edge2, int edge3)
    {
        int cost_for_first_seg=0;
        int cost_for_second_seg=0;
        int cost_for_third_seg=0;
        int cost_for_fourth_seg=0;

        //Input current route and cost
        int current_best_cost = Current_cost;//Cost of ABCD
        int all_best_cost=overall_best_cost;
        List<Integer> route = new ArrayList(Initial_route);


        int dummy_for_cost=0;
        int dummy_for_cost_2=0;
        int best_index = -1;
        int [][]Cost_matrix=g.getMatrix();


        //Calculate cost for fourth segment
        for (int i = edge3; i < route.size()-1; ++i)
            dummy_for_cost+=Cost_matrix[route.get(i)][route.get(i+1)];
        //cost_for_fourth_seg+=Cost_matrix[route.get(i)][route.get(i)+1];

        //Calculate cost for first segment
        for (int i = 0; i < edge1-1; ++i)
            dummy_for_cost+=Cost_matrix[route.get(i)][route.get(i+1)];
        //cost_for_first_seg+=Cost_matrix[route.get(i)][route.get(i)+1];

        //Calculate cost for second segment
        for (int i = edge1; i < edge2-1; ++i)
            dummy_for_cost+=Cost_matrix[route.get(i)][route.get(i+1)];
        //cost_for_second_seg+=Cost_matrix[route.get(i)][route.get(i)+1];

        //Calculate cost for third segment
        for (int i = edge2; i < edge3-1; ++i)
            dummy_for_cost+=Cost_matrix[route.get(i)][route.get(i+1)];
        //cost_for_third_seg(Current_route.get(i));

        //Combination(0.) : ABCD:
        if(current_best_cost<all_best_cost)
        {
            best_index=0;
            all_best_cost=current_best_cost;
        }


        //Combination(1.) : ACBD:
        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge2)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge3-1)][route.get(edge1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2-1)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=1;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;


        //Combination(2.) : ACB^{-1}D
        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge2)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge3-1)][route.get(edge2-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge1)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=2;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;



        //Combination(3.) : AB^{-1}CD

        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge2-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge1)][route.get(edge2)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge3-1)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=3;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;

        //Combination(4.) : A C^{-1} B D

        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge3-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2)][route.get(edge1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2-1)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=4;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;

        //Combination(5.) : A B C^{-1}D

        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2-1)][route.get(edge3-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=5;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;

        //Combination(6.) : AC^{-1}B^{-1}D

        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge3-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2)][route.get(edge2-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge1)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=6;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;
        //Combination(7.) : AB^{-1}C^{-1}D

        dummy_for_cost_2+=Cost_matrix[route.get(edge1-1)][route.get(edge2-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge1)][route.get(edge3-1)];
        dummy_for_cost_2+=Cost_matrix[route.get(edge2)][route.get(edge3)];
        if(dummy_for_cost+dummy_for_cost_2<all_best_cost)
        {
            best_index=7;
            all_best_cost=dummy_for_cost+dummy_for_cost_2;

        }
        dummy_for_cost_2=0;


        List<Integer> Best_route = new ArrayList<Integer>();
        if(best_index==(-1))
        {
            return Best_route;
        }

        else
        {
            Best_route=new ArrayList<>(Best_combination(route,best_index,edge1,edge2,edge3));
            Best_route.add(all_best_cost);


        }


        return Best_route;
    }


}

