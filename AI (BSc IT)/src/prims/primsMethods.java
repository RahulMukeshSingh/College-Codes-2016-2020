package prims;

import java.util.ArrayList;

public class primsMethods {
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
	public boolean containsInArrayString(String value, ArrayList<String> col) {
		boolean result = false;
		for (String c : col) {
			if (c.equals(value)) {
				result = true;
				break;
			}
		}
		return result;
	}
	public double maxInArrayy(double[][] values)
	{
		double max=0;
		for (double[] ds : values) {
			for (double d : ds) {
				if(d>max)
				{
					max=d;
				}
			}
		}
		return max;
	}
}
