package graph;

import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author Courtney Wong
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * Adjacency list class for performing DFS in the Approx class
 */

public class AdjacencyList <E> {
	private final int n;
	private int m;
	private LinkedList<Integer>[] list;
	
	public AdjacencyList(int n) {
		this.n = n;
		this.list = new LinkedList[n];
		for (int ii = 0; ii < n; ii++) {
			this.list[ii] = new LinkedList<Integer>();
		}
	}
	
	/**
	 * Adds an edge at a random index in order to randomize DFS
	 * @param a one end node
	 * @param b the other end node
	 * @param seed
	 */
	public void addEdge(int a, int b, int seed) {
		Random rand = new Random(seed);
		
		if (this.list[a].size() == 0) {
			this.list[a].add(b);
		} else {
			this.list[a].add(rand.nextInt(this.list[a].size()), b);
		}
		
		if (this.list[b].size() == 0) {
			this.list[b].add(a);
		} else {
			this.list[b].add(rand.nextInt(this.list[b].size()), a);
		}
		
		this.m++;
	}
	
	/**
	 * Gets the number of nodes in this adjacency list
	 * @return n number of nodes
	 */
	public int numNodes() {
		return this.n;
	}
	
	/**
	 * Gets the number of unique edges in this adjacency list
	 * @return m number of edges
	 */
	public int numEdges() {
		return this.m;
	}
	
	/**
	 * Gets the linked edge list of a specified node
	 * @param n the input node
	 * @return the linked edge list of n
	 */
	public LinkedList<Integer> getEdgeList(int n) {
		return this.list[n];
	}
	
	/**
	 * Prints this adjacency list with one node per line in the format <node1> -> [<edge1>, <edge2>, <edge3>...]
	 */
	public void print() {
		for (int ii = 0; ii < n; ii++) {
			System.out.println(String.format("%d -> %s", ii, list[ii].toString()));
		}
	}
}
