package prims;

import java.util.ArrayList;

public class primsAlgo {

	public static void main(String[] args) {
		ArrayList<transportationStructure> data = (new csvread()).csvreadTransportation("E:\\prims.csv");
		ArrayList<String> places = data.get(0).places;
		double[][] values = data.get(0).values;
		ArrayList<String> done = new ArrayList<String>();
		done.add(places.get(0));
		primsMethods pm = new primsMethods();
		double sum = 0;
		int i = 0;
		while (i<values.length-1) {
			double min = pm.maxInArrayy(values);
			String from="";
			String temp = "";
			for (String pl : done) {
				for (String p : places) {
					if (!p.equalsIgnoreCase(pl) && !pm.containsInArrayString(p, done)) {
						if (pm.getEachDistanceIndex(pl, p, values, places) < min) {
							min = pm.getEachDistanceIndex(pl, p, values, places);
							temp = p;
							from= pl;
						}
					}
				}
			}
			
			done.add(temp);

			sum += min;
			System.out.println(from+" - "+temp+" = "+min);
			i++;
		}
		System.out.println("Minimum Distance : "+sum);

		
	}

}
