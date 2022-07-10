package AlgorithmsPrac8;

import java.util.ArrayList;
import java.util.Scanner;

class KruskalDS {
	String from, to;
	double cost;

	public KruskalDS(String from, String to, double cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

}

class ClusterData {
	String clustName, vertex;

	public ClusterData(String clustName, String vertex) {
		this.clustName = clustName;
		this.vertex = vertex;
	}

}

public class KruskalMST {
	private ArrayList<ClusterData> clustData;

	public KruskalDS[] sortCost(KruskalDS[] in) {
		int len = in.length;
		int i, j;
		KruskalDS k;
		for (i = 1; i < len; i++) {
			j = i - 1;
			k = in[i];
			while (j >= 0 && k.cost < in[j].cost) {
				in[j + 1] = in[j];
				j--;
			}
			in[j + 1] = k;
		}

		return in;
	}

	public String vertexClustName(String vertex) {
		String flag = null;
		for (ClusterData cd : clustData) {
			if (cd.vertex.equalsIgnoreCase(vertex)) {
				flag = cd.clustName;
				break;
			}
		}
		return flag;
	}

	public void replaceClustName(String replace, String replaceWith) {
		for (ClusterData cd : clustData) {
			if (cd.clustName.equalsIgnoreCase(replace)) {
				cd.clustName = replaceWith;
			}
		}
	}

	public boolean isSpanningTree(int vLen) {
		boolean flag;
		if (clustData.size() == vLen) {
			flag = true;
			String firstClustName = clustData.get(0).clustName;
			for (ClusterData cd : clustData) {
				if (!cd.clustName.equalsIgnoreCase(firstClustName)) {
					flag = false;
					break;
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public void Kruskal(KruskalDS[] in, int vLen) {
		in = sortCost(in);

		clustData = new ArrayList<ClusterData>();
		ArrayList<KruskalDS> MST = new ArrayList<KruskalDS>();
		for (KruskalDS edge : in) {
			String fromClustName = vertexClustName(edge.from);
			String toClustName = vertexClustName(edge.to);

			if (fromClustName == null && toClustName == null) {
				String clustName = edge.from;
				clustData.add(new ClusterData(clustName, edge.from));
				clustData.add(new ClusterData(clustName, edge.to));
				MST.add(edge);
			} else if (fromClustName == null) {
				clustData.add(new ClusterData(toClustName, edge.from));
				MST.add(edge);
			} else if (toClustName == null) {
				clustData.add(new ClusterData(fromClustName, edge.to));
				MST.add(edge);
			} else {
				if (!fromClustName.equalsIgnoreCase(toClustName)) {
					replaceClustName(toClustName, fromClustName);
					MST.add(edge);
				}
			}
		}

		displayMST(MST, vLen);
	}

	public void displayMST(ArrayList<KruskalDS> MST, int vLen) {
		if (isSpanningTree(vLen)) {
			double minTotalCost = 0;
			System.out.println("From" + "---" + "Cost" + "---" + ">>>" + "To");
			for (KruskalDS m : MST) {
				System.out.println(m.from + "---" + m.cost + "---" + ">>>" + m.to);
				minTotalCost += m.cost;
			}
			System.out.println("Minimum Spanning Tree Total Cost : " + minTotalCost);
		} else {
			try {
				throw new Exception("No Spanning Tree!!!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Number of Vertex : ");
		int vLen = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter The Number Of Edges : ");
		int len = sc.nextInt();
		sc.nextLine();
		KruskalDS[] in = new KruskalDS[len];
		System.out.println("Enter The " + len + " Edges (from,to,cost) : ");
		for (int i = 0; i < in.length; i++) {
			String[] inData = sc.nextLine().split(",");
			in[i] = new KruskalDS(inData[0].trim(), inData[1].trim(), Double.parseDouble(inData[2].trim()));
		}
		KruskalMST km = new KruskalMST();
		km.Kruskal(in, vLen);

	}

}