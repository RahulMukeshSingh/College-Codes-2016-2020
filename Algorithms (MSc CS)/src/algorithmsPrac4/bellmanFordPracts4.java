package algorithmsPrac4;

import java.util.Scanner;

class inputData {
	String fromVertex, toVertex;
	double cost;

	public inputData(String fromVertex, String toVertex, double cost) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.cost = cost;
	}

}

class minDist {
	String vertex, previousVertex = null;
	double minCost = Double.MAX_VALUE;

	public minDist() {
	}

	public minDist(String vertex, String sourceVertex) {
		this.vertex = vertex;
		if (vertex.equalsIgnoreCase(sourceVertex)) {
			this.minCost = 0;
		}
	}

	public void setMinDistValues(minDist[] minDistVertices, String vertex, double minCost, String previousVertex) {
		for (minDist md : minDistVertices) {
			if (md.vertex.equalsIgnoreCase(vertex)) {
				md.previousVertex = previousVertex;
				md.minCost = minCost;
				break;
			}
		}
	}

	public double getMinDist(minDist[] minDistVertices, String vertex) {
		double minDistV = 0;
		for (minDist md : minDistVertices) {
			if (md.vertex.equalsIgnoreCase(vertex)) {
				minDistV = md.minCost;
				break;
			}
		}
		return minDistV;
	}

	public String getPreviousVertex(minDist[] minDistVertices, String currentVertex) {
		String previousVertexOfCurrent = "";
		for (minDist md : minDistVertices) {
			if (md.vertex.equalsIgnoreCase(currentVertex)) {
				previousVertexOfCurrent = md.previousVertex;
				break;
			}
		}
		return previousVertexOfCurrent;
	}

}

public class bellmanFordPracts4 {

	public boolean calculateMinDist(minDist[] minDistVertices, inputData[] verticesAndEdges, int noOfVertices) {
		boolean isNegWeightCycle = false;
		minDist md = new minDist();
		for (int i = 0; i < noOfVertices; i++) {
			for (inputData vae : verticesAndEdges) {
				double minDistOfFrom = md.getMinDist(minDistVertices, vae.fromVertex);
				double minDistOfTo = md.getMinDist(minDistVertices, vae.toVertex);
				if ((minDistOfFrom + vae.cost) < minDistOfTo) {
					if (i == (noOfVertices - 1)) {
						isNegWeightCycle = true;
					} else {
						md.setMinDistValues(minDistVertices, vae.toVertex, (minDistOfFrom + vae.cost), vae.fromVertex);
					}
				}
			}
		}

		return isNegWeightCycle;
	}

	public String showMinPath(boolean isNegWeightCycle, minDist[] minDistVertices, String sourceVertex,
			String destinationVertex) {
		String minPath;
		minDist md = new minDist();
		if (isNegWeightCycle) {
			minPath = "It's a Negative Weight Cycle!!!";
		} else {
			minPath = destinationVertex;
			String currentVertex = destinationVertex;
			while (!currentVertex.equalsIgnoreCase(sourceVertex)) {
				String previousVertex = md.getPreviousVertex(minDistVertices, currentVertex);
				minPath = previousVertex + " -> " + minPath;
				currentVertex = previousVertex;
			}
			minPath = "Final Path : " + minPath + "\nTotal Minimum Cost Path : "+ md.getMinDist(minDistVertices, destinationVertex);
		}
		return minPath;
	}

	public static void main(String[] args) {
		bellmanFordPracts4 bfp = new bellmanFordPracts4();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of vertex : ");
		int noOfVertices = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the number of edges : ");
		int noOfEdges = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the source vertex : ");
		String sourceVertex = sc.nextLine();
		System.out.println("Enter the destination vertex : ");
		String destinationVertex = sc.nextLine();
		minDist[] minDistVertices = new minDist[noOfVertices];
		System.out.println("Enter name of following vertices : ");
		for (int i = 0; i < noOfVertices; i++) {
			System.out.println("Vertex " + (i + 1) + " : ");
			minDistVertices[i] = new minDist(sc.nextLine(), sourceVertex);
		}
		inputData[] verticesAndEdges = new inputData[noOfEdges];
		System.out.println("Enter the details of following edges (From Vertex, To Vertex, Cost): ");
		for (int i = 0; i < noOfEdges; i++) {
			String[] details = sc.nextLine().split(",");
			String fromVertex = details[0];
			String toVertex = details[1];
			double cost = Double.parseDouble(details[2]);
			verticesAndEdges[i] = new inputData(fromVertex, toVertex, cost);
		}
		boolean isNegWeightCycle = bfp.calculateMinDist(minDistVertices, verticesAndEdges, noOfVertices);
		if (!isNegWeightCycle) {
			System.out.println("Final Details : ");
			System.out.println("Vertex\tMinimum Cost\tPrevious Vertex");
			for (minDist md : minDistVertices) {
				System.out.println(md.vertex + "\t" + md.minCost + "\t\t" + md.previousVertex);
			}
		}
		String minPathDetails = bfp.showMinPath(isNegWeightCycle, minDistVertices, sourceVertex, destinationVertex);
		System.out.println(minPathDetails);
	}
}
