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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

        // Condition check
        if(matrix == null || coords.size() == 0){
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
            }
        }

        Graph g = new Graph(coords.size(),matrix);
        return g;
    }

    public static void deleteFileIfExist(String filePath){
        File f = new File(filePath);
        if(f.exists()){
            f.delete();
        }
        return;
    }

}
