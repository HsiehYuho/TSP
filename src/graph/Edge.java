package graph;

/**
 * 
 * @author Courtney Wong
 * Georgia Institute of Technology, Fall 2018
 * CSE6140 Project, Travel Salesman Problem
 * 
 * The Edge class is used as an additional data structure to represent
 * the given TSP graph, used by the MST class in priority queues to sort
 * edges by their weights.
 */

public class Edge implements Comparable<Edge> {
	
	private final int x;
	private final int y;
	private final int cost;
	
	/**
	 * Instantiates an edge between two nodes with the inputted indices and its cost
	 * @param x one node's index
	 * @param y the other node's index
	 * @param cost if either x, y, or cost are negative
	 * @throws IllegalArgumentException if x, y, and/or cost is negative
	 */
	public Edge(int x, int y, int cost) {
        if (x < 0) throw new IllegalArgumentException("Node index must be a nonnegative integer");
        if (y < 0) throw new IllegalArgumentException("Node index must be a nonnegative integer");
        if (cost < 0) throw new IllegalArgumentException("Edge cost msut be a nonnegative integer for TSP");
		
		this.x = x;
		this.y = y;
		this.cost = cost;
	}
	
	/*
	 * Compare two edges by their weights
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Edge e) {
		return Integer.compare(this.cost, e.cost);
	}
	
	/**
	 * Return the weight/cost of an edge
	 * @param e the edge
	 * @return the integer cost of the edge
	 */
	public int getCost(Edge e) {
		return e.cost;
	}
	
	/**
	 * Summarize the attributes of an edge into a printable string
	 * @param e the edge
	 * @return a String of the format "node1~node2 cost"
	 */
	@Override
	public String toString() {
		return String.format("%d~%d %d", this.x, this.y, this.cost);
	}
}
