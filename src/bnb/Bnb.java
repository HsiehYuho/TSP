package bnb;

import graph.Graph;

import java.util.*;

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


        // curSec = (double)System.currentTimeMillis()/1000.0;
        //}
        calculateMulti(g);
        return g;
    }

    // During your computation, please call g.addApproxResult(apporxCost, timeStamp)) once you find a better cost
    // Also, please use g.setCurrentBestResult(int cost, List<Integer> routes) to update the solution

    private static void calculateMulti(Graph g){
        int[][] matrix = g.getMatrix();

        // For test
//        int max = Integer.MAX_VALUE;
//        int[][] matrix = {{max,3,9,7},{3,max,6,5},{5,6,max,6},{9,7,4,max}};

        Queue<Node> pq = new PriorityQueue<>((a, b) -> a.getCost() - b.getCost());

        List<Integer> knownBestRoutes = new ArrayList<>();

        // Get the init known best solution cost as lower bound
        int knownBestCost = Util.findGreedyCost(matrix,knownBestRoutes);
        g.setCurrentBestResult(knownBestCost, knownBestRoutes);
        System.out.println("Init best known cost: " + knownBestCost);

        Set<Integer> unvisited = new HashSet<>();
        for(int i = 1; i < matrix.length; i++){
            unvisited.add(i);
        }
//        Node root = new NodeMulti(null, 0, 0, matrix, unvisited);
        Node root = new NodeBinary(null, 0, 0, matrix, unvisited);
        pq.add(root);

        while(pq.size() != 0){
            Node node = pq.poll();
            Node[] children = node.genChildren();

            // deadend
            if(children == null){
                continue;
            }

            // leaf of tree
            if(children.length == 0){
                if(node.getCost()< knownBestCost){
                    knownBestCost = node.getCost();
                    knownBestRoutes = node.getPath();
                    knownBestRoutes.add(0);
                }
            }
            else{
                for(Node n : children){
                    if(n.getCost() > knownBestCost){
                        continue;
                    }
                    pq.add(n);
                }
            }
        }

        // Validate
        System.out.println(knownBestCost);
        for(int i : knownBestRoutes){
            System.out.print(i + "\t");
        }

        // Store result into graph
        g.setCurrentBestResult(knownBestCost,knownBestRoutes);

        return;
    }
    private static void calculateBinary(Graph g) {
        int[][] matrix = g.getMatrix();
//        int max = Integer.MAX_VALUE;
//        int[][] matrix = {{max,5,3,2},{5,max,1,4},{3,1,max,4},{2,4,4,max}};

//        Queue<NodeMulti> pq = new PriorityQueue<>((a, b) -> a.getCost() - b.getCost());


    }







}