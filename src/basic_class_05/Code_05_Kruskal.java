package basic_class_05;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//undirected graph only
public class Code_05_Kruskal {

	// Union-fine set
	public static class UnionFind {
		private HashMap<Node, Node> fatherMap;
		private HashMap<Node, Integer> rankMap;

		public UnionFind() {
			fatherMap = new HashMap<>();
			rankMap = new HashMap<>();
		}

		private Node findFather(Node n) {
			Node father = fatherMap.get(n);
			if (father != n) {
				father = findFather(father);
			}
			fatherMap.put(n, father);
			return father;
		}

		public void makeSets(Collection<Node> nodes) {
			fatherMap.clear();
			rankMap.clear();
			for (Node t : nodes) {
				fatherMap.put(t, t);
				rankMap.put(t, 1);
			}
		}

		public boolean isSameSet(Node a, Node b) {
			return findFather(a) == findFather(b);
		}

		public void union(Node a, Node b) {
			if (a == null || b == null)
				return;
			Node aFather = findFather(a);
			Node bFather = findFather(b);
			if (aFather != bFather) {
				int aRank = rankMap.get(aFather);
				int bRank = rankMap.get(bFather);
				if (aRank < bRank) {
					fatherMap.put(aFather, bFather);
					rankMap.put(bFather, aRank + bRank);
				} else {
					fatherMap.put(bFather, aFather);
					rankMap.put(aFather, aRank + bRank);
				}
			}
		}
	}

	public static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}
	}

	public static Set<Edge> kruskalMST(Graph graph) {
		UnionFind unionFind = new UnionFind();
		unionFind.makeSets(graph.nodes.values());
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
		for (Edge edge : graph.edges) {
			priorityQueue.add(edge);
		}
		Set<Edge> result = new HashSet<>();
		while (!priorityQueue.isEmpty()) {
			Edge edge = priorityQueue.poll();
			if (!unionFind.isSameSet(edge.from, edge.to)) {
				result.add(edge);
				unionFind.union(edge.from, edge.to);
			}
		}
		return result;
	}
}
