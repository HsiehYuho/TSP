/**
 * @author Yu-Ho Hsieh
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Entry Point
 *
 */

package files;

import graph.Graph;
import graph.Edge;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ReadWriteFile {
    private static String EDGE_WEIGHT_TYPE = "EDGE_WEIGHT_TYPE";
    private static String EUC_2D = "EUC_2D";
    private static String GEO = "GEO";
    private static String NODE_COORD_SECTION = "NODE_COORD_SECTION";
    private static String EOF = "EOF";
    private static String DIMENSION = "DIMENSION";

    public static Graph readFile(String filePath) throws Exception {
        // Init variables
        boolean is_node_section = false;
        String line = null;
        String fileType = "";
        List<Coord> coords = new ArrayList<>();
        int[][] matrix = null;
        PriorityQueue<Edge> edgeList = null;

        // Open the file
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        while((line = br.readLine()) != null){
            line = line.trim();

            if(line.equalsIgnoreCase(EOF) || line.equals("")){
                break;
            }

            // Non numbers part
            if(!is_node_section){
                if(line.equalsIgnoreCase(NODE_COORD_SECTION)){
                    is_node_section = true;
                }
                else if(line.toUpperCase().startsWith(DIMENSION)){
                    int size = Integer.valueOf(line.substring(line.indexOf(":")+1).trim());
                    matrix = new int[size][size];
                    edgeList = new PriorityQueue<Edge>(size*(size-1)/2);
                    	// Number of unique edges in a complete graph with n nodes = n*(n-1)/2

                }
                else if(line.toUpperCase().startsWith(EDGE_WEIGHT_TYPE)){
                    if(line.toUpperCase().endsWith(EUC_2D)){
                        fileType = EUC_2D;
                    }
                    else if (line.toUpperCase().endsWith(GEO)){
                        fileType = GEO;
                    }
                }

            }
            // Numbers part
            else{
                // [0] id, [1] x coord, [2] y coord
                String[] tokens = line.split("\\s+");
                if(fileType.equals(EUC_2D)){
                    double x = Double.valueOf(tokens[1]);
                    double y = Double.valueOf(tokens[2]);
                    coords.add(new Coord(x,y));

                }
                else if (fileType.equals(GEO)){
                    double x = Double.valueOf(tokens[1]);
                    double y = Double.valueOf(tokens[2]);
                    int degX = (int)x;
                    int degY = (int)y;
                    double minX = x - degX;
                    double minY = y - degY;
                    double radX = Math.PI * (degX + 5.0 * minX/ 3.0) / 180.0;
                    double radY = Math.PI * (degY + 5.0 * minY/ 3.0) / 180.0;
                    coords.add(new Coord(radX,radY));
                }
                else{
                    throw new RuntimeException("Unknown file type");
                }
            }
        }
        
        br.close();

        // Condition check
        if(matrix == null || edgeList == null || coords.size() == 0){
            throw new RuntimeException("No dimension or nodes found");
        }

        // convert coords to graph
        for(int i = 0; i < coords.size(); i++){
            for(int j = 0; j < coords.size(); j++){
                // This is not a good way to init, however, in this project, all nodes are connected with each other
                int distance = Integer.MAX_VALUE;
                if(i == j){
                    matrix[i][j] = matrix[j][i] = distance;
                    continue;
                }
                double diffX = coords.get(i).getX() - coords.get(j).getX();
                double aggreX =  coords.get(i).getX() + coords.get(j).getX();
                double diffY = coords.get(i).getY() - coords.get(j).getY();


                if(fileType.equals(EUC_2D)){
                    distance = (int)Math.round(Math.sqrt(Math.pow(diffX,2) + Math.pow(diffY,2)));

                }
                else if (fileType.equals(GEO)){
                    double RRR = 6378.388;
                    double q1 = Math.cos(diffY);
                    double q2 = Math.cos(diffX);
                    double q3 = Math.cos(aggreX);
                    distance = (int) (RRR * Math.acos(0.5 * ((1.0 + q1)* q2  - (1.0 - q1)*q3)) + 1.0);
                }
                else{
                    throw new RuntimeException("Unknown file type");
                }
                matrix[i][j] = matrix[j][i] = distance;
                if (j > i) { // Avoids duplicate edges
                	edgeList.add(new Edge(i, j, distance));	
                }
            }
        }

        Graph g = new Graph(coords.size(),matrix,edgeList);
        return g;
    }

    public static void deleteFileIfExist(String filePath){
        File f = new File(filePath);
        if(f.exists()){
            f.delete();
        }
        return;
    }

    public static void writeFile(String solFile, String traceFile, Graph g) throws IOException {

        Path solPath = Paths.get(solFile);
        Path tracePath = Paths.get(traceFile);

        Files.createDirectories(solPath.getParent());
        Files.createFile(solPath);

        Files.createDirectories(tracePath.getParent());
        Files.createFile(tracePath);

        File sol = new File(solFile);
        File trace = new File(traceFile);



        PrintWriter solPw = new PrintWriter(sol);
        PrintWriter tracePw = new PrintWriter(trace);

        List<Integer> costs = g.getApproxCosts();
        List<Double> timeStamps = g.getTimeStamps();


        for(int i = 0; i < costs.size(); i++){
            int cost = costs.get(i);
            double timeStamp = timeStamps.get(i);
            tracePw.printf("%.2f, %d \n", timeStamp, cost);
            System.out.printf("At time %.2f, found cost %d \n",timeStamp, cost);
        }

        // Output current best result
        int knownBestCost = g.getCurrentBestCost();
        List<Integer> knownBestRoutes = g.getCurrentBestRoutes();
        if(knownBestCost != Integer.MAX_VALUE){
            solPw.println(knownBestCost);
            System.out.printf("Best known cost: %d \n", knownBestCost);
        }
        else{
            solPw.println("Did not find any route");
            System.out.printf("Cannot find any cost \n");

        }
        for(int i = 0; i < knownBestRoutes.size(); i++){
            int id = knownBestRoutes.get(i);
            solPw.print(id);
            if(i != knownBestRoutes.size() - 1){
                solPw.print(",");
            }
            System.out.printf("%d ", id);
        }
        System.out.println();
        solPw.close();
        tracePw.close();


    }

}
