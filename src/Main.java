import approx.Approx;
import bnb.Bnb;
import ls1.Ls1;
import ls2.Ls2;
import files.ReadWriteFile;
import graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
/**
 * @author Yu-Ho Hsieh
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Entry Point
 *
 */

public class Main {

    /**
     * exec -inst <filename> -alg [BnB | Approx | LS1 | LS2]  -time <cutoff_in_seconds> [-seed <random_seed>]
     */
    private static String INST = "inst";

    private static String ALG = "alg";
    private final static String BNB = "BnB";
    private final static String APPROX = "Approx";
    private final static String LS1 = "LS1";
    private final static String LS2 = "LS2";

    private static String TIME = "time";
    private static String SEED = "seed";

    private static List<String> options = new ArrayList<>();
    private static Map<String, String> arguments = new HashMap<>();


    public static void main(String[] args) throws Exception {

        if(args.length != 6 && args.length != 8){
            throw new IllegalArgumentException("The arguments number should be 3 or 4");
        }
        init();

        // Store args
        for(int i = 0; i < args.length; i+=2){
            String option = args[i];
            String arg = args[i+1];
            if(!option.startsWith("-")){
                throw new IllegalArgumentException("Argument does not start with - : " + args[i]);
            }
            option = option.substring(option.indexOf("-")+1);
            if(!options.contains(option)){
                throw new IllegalArgumentException("Option not found: " + option );
            }

            arguments.put(option,arg);
        }

        String file = arguments.get(INST);
        String alg = arguments.get(ALG);
        int cutTime = Integer.valueOf(arguments.get(TIME)); // in seconds
        int seed = arguments.get(SEED) != null ? Integer.valueOf((arguments.get(SEED))) : 0;

        Graph g = ReadWriteFile.readFile(file);

        // Call functions
        switch(alg){
            case BNB: {
                g = Bnb.compute(g, cutTime);
                break;
            }
            case APPROX: {
                g = Approx.compute(g, cutTime, seed);
                break;
            }
            case LS1: {
                g = Ls1.compute(g, cutTime, seed);
                break;
            }
            case LS2: {
                g = Ls2.compute(g, cutTime, seed);
                break;
            }
            default:
                throw new IllegalArgumentException("alg option does not exist");
        }
        // Output log

        File resultFolder = new File("./results/");
        if(!resultFolder.exists()){
            resultFolder.mkdir();
        }
        // Examle solution filename jazz_LS1_600_4.sol
        String instance = arguments.get(INST);
        instance = instance.substring(0,instance.lastIndexOf("."));
        int slashIndex = instance.lastIndexOf("/");
        if(slashIndex != -1){
            instance = instance.substring(slashIndex+1);
        }
        String outputFile = instance + "_" + alg + "_" + cutTime;
        if(seed!= 0){
            outputFile += "_" + seed;
        }
        String solFile = "./results/" + outputFile + ".sol";
        String traceFile = "./results/" + outputFile + ".trace";

        files.ReadWriteFile.deleteFileIfExist(solFile);
        files.ReadWriteFile.deleteFileIfExist(traceFile);

        files.ReadWriteFile.writeFile(solFile,traceFile,g);

        System.out.println("Done");
    }

    private static void init(){
        options.add(INST);
        options.add(ALG);
        options.add(TIME);
        options.add(SEED);
    }
}
