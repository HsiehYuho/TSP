package approx;



/**
 * 
 * @author Courtney Wong
 * Georgia Institute of Technology, Fall 2018
 *
 * CSE6140 Project, Travel Salesman Problem
 * UnionFind data structure to union and find singletons for MST implementation
 *
 */
public class UnionFind {
	private int[] parents;
	private int[] rank;
	
	public UnionFind(int size) {
		this.rank = new int[size]; // initially all rank 0
		this.parents = new int[size];
		
		for (int ii = 0; ii < size; ii++) {
			this.parents[ii] = ii; // each node starts out as its own parent
		}
	}
	
	/**
	 * Finds the parent of node v and compresses the tree with recursion
	 * @param v the index of the node
	 * @return the index of the parent node
	 */
	public int find(int ii) {
		if (this.parents[ii] != ii) {
			this.parents[ii] = this.find(this.parents[ii]);
		}
		return this.parents[ii];
	}
	
	/**
	 * Joins the two sets in the case that they do not already share a parent
	 * @param uRoot
	 * @param vRoot
	 */
	public void union(int uRoot, int vRoot) {
		if (this.rank[uRoot] > this.rank[vRoot]) {
			this.parents[vRoot] = uRoot;
		} else if(this.rank[uRoot] < this.rank[vRoot]) {
			this.parents[uRoot] = vRoot;
		} else { // ranks are equal
			this.parents[uRoot] = vRoot;
			this.rank[vRoot]++;
		}
	}
}
