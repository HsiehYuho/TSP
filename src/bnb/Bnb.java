package bnb;

import graph.Graph;

import java.util.*;

/**
 * @author Yu-Ho
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Branch and bound computation method
 */

public class Bnb {
    public static Graph compute(Graph g, int cutTime) {
        calculate(g,cutTime);
        return g;
    }

    private static void calculate(Graph g, int cutTime){
        // set up timing
        double startSec = (double)System.currentTimeMillis()/1000.0;
        double curSec = startSec;

        // Cuz latter the non-take branch will modify the matrix, so we need to reserve the original one
        int[][] matrix = Util.cloneMatrix(g.getMatrix());

        Queue<Node> pq = new PriorityQueue<>((a, b) -> a.getLowerBound() - b.getLowerBound());

        // For first several levels, we will pick "selected" branch directly, and store others into buffer
        Stack<Node> buffer = new Stack<>();
        boolean bufferFlag = false;
        List<Integer> knownBestRoutes = new ArrayList<>();

        int knownBestCost = Integer.MAX_VALUE;

        // Get the init known best solution cost as lower bound to optimize the code
        knownBestCost = Util.findGreedyCost(matrix,knownBestRoutes);
        g.setCurrentBestResult(knownBestCost, knownBestRoutes);

        // Remove the comment if want to test the multi ways of computation
        Node root = new NodeBinary(new ArrayList<>(), 0, matrix, matrix.length, 0);
        pq.add(root);

        while(pq.size() != 0 && (curSec - startSec) < cutTime){
            Node node = pq.poll();
            Node[] children = node.genChildren();

            // leaf of tree
            if(children.length == 0){
                List<Integer> path = node.getPath();
                int cost = utils.GraphUtils.calculateCost(g,path);

                // Best solution
                if(cost < knownBestCost){
                    knownBestCost = cost;
                    knownBestRoutes = path;

                    // Set up record
                    curSec = (double)System.currentTimeMillis()/1000.0;
                    g.addApproxResult(knownBestCost,curSec-startSec);
                }
            }
            else{
                Node n1 = children[0];
                Node n2 = children[1];

                // node = null means it is dead end
                if(n1 != null && n1.getLowerBound() < knownBestCost){
                    pq.add(n1);
                }
                if(n2 != null && n2.getLowerBound() < knownBestCost ){
                    if(n2.getDepth() > (matrix.length * matrix.length) - 30 || bufferFlag){
                        pq.add(n2);
                        if(pq.size() > 10000){
                            bufferFlag = false;
                        }
                    }
                    else{
                        buffer.add(n2);
                    }
                }
            }

            // update current time
            curSec = (double)System.currentTimeMillis()/1000.0;

            // if pq is empty, add buffer into pq
            if(pq.size() == 0 && buffer.size() != 0){
                for(int i = 0; i < 30 && buffer.size() != 0; i++){
                    if(buffer.peek().getLowerBound() > knownBestCost){
                        buffer.pop();
                        i--;
                    }
                    else{
                        pq.add(buffer.pop());
                    }
                }
                bufferFlag = true;
            }
        }

        if(curSec - startSec >= cutTime){
            System.out.printf("Cut off: %.2f secs \n", curSec - startSec);
        }
        else{
            System.out.printf("Finish: %.2f secs \n", curSec - startSec);
        }

        // Store result into graph
        g.setCurrentBestResult(knownBestCost,knownBestRoutes);

        return;
    }

}