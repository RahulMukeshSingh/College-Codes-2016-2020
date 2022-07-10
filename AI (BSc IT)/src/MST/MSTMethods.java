package MST;

import java.util.ArrayList;

public class MSTMethods {
	public double getEachDistanceIndex(String A, String B, double[][] distance, ArrayList<String> places) {
		int AIndex = 0, BIndex = 0;
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).equals(A)) {
				AIndex = i;
			}
			if (places.get(i).equals(B)) {
				BIndex = i;
			}
		}

		return distance[AIndex][BIndex];
	}

	public boolean containsInArrayListString(String value, ArrayList<String> col) {
		boolean result = false;
		for (String c : col) {
			if (c.equals(value)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean containsInArrayString(String value, String[] col) {
		boolean result = false;
		for (String c : col) {
			if (c.equals(value)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public double maxInArrayy(double[][] values) {
		double max = 0;
		for (double[] ds : values) {
			for (double d : ds) {
				if (d > max) {
					max = d;
				}
			}
		}
		return max;
	}

	public ArrayList<MSTStructure> getTopNDistance(ArrayList<MSTStructure> mstStruct, int noOfClusters) {
		ArrayList<MSTStructure> topNDistance = new ArrayList<MSTStructure>();
		MSTStructure temp = null;
		for (int i = 0; i < mstStruct.size(); i++) {
			for (int j = i; j < mstStruct.size(); j++) {
				if (mstStruct.get(i).distance > mstStruct.get(j).distance) {
					temp = mstStruct.get(i);
					mstStruct.set(i, mstStruct.get(j));
					mstStruct.set(j, temp);
				}
			}

		}
		for (int i = 0; i < mstStruct.size() - (noOfClusters - 1); i++) {
			topNDistance.add(mstStruct.get(i));
		}
		return topNDistance;
	}

	public String getInitialCluster(ArrayList<MSTStructure> topNDistance, ArrayList<String> clusters) {
		String clusts = "";
		for (String cluster : clusters) {
			clusts += cluster + " ";
		}
		clusts = clusts.trim();
		String initial = "";
		String[] clust = clusts.split(" ");
		for (MSTStructure tnd : topNDistance) {
			if (!containsInArrayString(tnd.from, clust) && !containsInArrayString(tnd.to, clust)) {
				initial = tnd.from + " " + tnd.to;
			}
		}

		return initial.trim();
	}
}
