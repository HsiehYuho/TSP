# TSP
CSE 6140 Final Project 

Team members: 
* Asra Yousuf  
* Courtney Wong 
* Puting Yu (Roddick)
* Yu-Ho Hsieh

## Description

-------------

This is a program to solve traveling salesman problem (TSP) using 4 different kinds of algorithm, 
including Branch and Bound, Approximation, and 2 Local Search.

## Folder Structure

-------------


├── src         # Source files 
    
  ├── main      # Entry point

  ├── approx    # Approximation package (Courtney)
  
  ├── bnb       # Branch and Bound (Yu-Ho)
    
  ├── ls1       # Local Search 1 (Roddick)

  ├── ls2       # Local Search 2 (Asra)
  
  ├── files     # package for file reading and writing API
  
  ├── graph     # package for storing graph and output format

├── data        # TSP data

├── results		# results of output file

└── README.md    

## Compilation & Experiment

-------------

If you want to compile it via terminal, please cd to the folder *src*, then run

```
javac -cp . Main.java
```

Then you should find some .class files appearing in your packages folders and src folder.
They are compiled files and can be executed.


Then if you run the following command, you should be able to run the program.

```
java Main -inst ../data/Cincinnati.tsp -alg BnB -time 600 
```

* Please make sure you are in *src* folder. 
* The output *results* folder should appear under *src* folder.

## Results 

The code itself outputs the files which start from index 1 as requriement of handout. 
However, in the *results* folder, everything is correct except for the index start from 0. We do not have time to re-run every instance. 


## Compute function structure

-------------
* Please maintain a public and static function called 
*compute* in each algorithm. 

* Please remember to use the following two APIs to update solution when you find out new better solution. 

```
// timeStamp is the second from the program start till now
// Ex: approxCost = 13155, timeStamp = 110
// Means after running 110 seconds, the algorithm found a route cost 13155
graph.addApproxResult(int apporxCost, double timeStamp))  

// The routes may start from "0", ex "0->1->5->4->3->2->0", but that is not a must!
// The routes go back to origin ex "0->1->5->4->3->2->0", or "5->4->3->2->0->1->5"
graph.setCurrentBestResult(int cost, List<Integer> routes) // routes.size()  != # of nodes, but = # of nodes (cities) + 1
```

## Related Links for Analysis and Report Generation
* Overleaf/LaTex: https://www.overleaf.com/4622789921hhfkchdhmhdd
* LaTeX table generator: https://www.tablesgenerator.com
    * See downloadable file under analysis
* Sample TSP files and solutions from U of Waterloo: http://www.math.uwaterloo.ca/tsp/world/countries.html
* Additional sample TSP files and solutions: http://elib.zib.de/pub/mp-testdata/tsp/tsplib/stsp-sol.html
* Raw data Google spreadsheet: https://docs.google.com/spreadsheets/d/1LSfLjVvHYG1H_9V1zsPS1FUmDeIfpaEw54vEjBwoXCA/edit?usp=sharing
