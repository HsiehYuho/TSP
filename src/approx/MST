package approx;

import graph.Graph;
import graph.Edge;
import graph.AdjacencyList;
import java.util.PriorityQueue;

public class MST {
	public static AdjacencyList<Integer> compute_MST(Graph g, int seed) {
		PriorityQueue<Edge> edgeList = g.getEdgeList();
		AdjacencyList<Integer> mst = new AdjacencyList<Integer>(g.getSize());
		UnionFind uf = new UnionFind(g.getSize()); // Initialize n subsets containing singletons
		Edge cur;
		int x, y;
		
		while (mst.numEdges() < g.getSize()-1) { // number of edges in tree = # nodes - 1
			cur = edgeList.poll();
			x = uf.find(cur.getNode1());
			y = uf.find(cur.getNode2());
			if (x != y) {
				mst.addEdge(cur.getNode1(), cur.getNode2(), seed);
				uf.union(x, y);
			}
		}
		
		return mst;
	}
}
