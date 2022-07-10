package MST;

import java.util.ArrayList;
import java.util.Scanner;

public class MSTAlgo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("No of Clusters : ");
		int noOfClusters = sc.nextInt();
		sc.nextLine();

		ArrayList<transportationStructure> data = (new csvread()).csvreadTransportation("E:\\transportationMST.csv");
		ArrayList<String> places = data.get(0).places;
		double[][] values = data.get(0).values;
		ArrayList<String> done = new ArrayList<String>();
		done.add(places.get(0));
		MSTMethods mm = new MSTMethods();
		ArrayList<MSTStructure> mstStruct = new ArrayList<MSTStructure>();
		int i = 0;
		while (i < values.length - 1) {
			double min = mm.maxInArrayy(values);
			String from = "";
			String temp = "";
			for (String pl : done) {
				for (String p : places) {
					if (!p.equalsIgnoreCase(pl) && !mm.containsInArrayListString(p, done)) {
						if (mm.getEachDistanceIndex(pl, p, values, places) < min) {
							min = mm.getEachDistanceIndex(pl, p, values, places);
							temp = p;
							from = pl;
						}
					}
				}
			}

			done.add(temp);
			mstStruct.add(new MSTStructure(from, temp, min));
			i++;
		}

		ArrayList<MSTStructure> topNDistance = mm.getTopNDistance(mstStruct, noOfClusters);

		ArrayList<String> clusters = new ArrayList<String>();

		for (int j = 0; j < noOfClusters; j++) {
			clusters.add("");
			clusters.set(j, mm.getInitialCluster(topNDistance, clusters));
			for (int k = 0; k < places.size() - 1; k++) {
				for (MSTStructure mst : topNDistance) {
					if (mm.containsInArrayString(mst.from, clusters.get(j).split(" "))
							&& !mm.containsInArrayString(mst.to, clusters.get(j).split(" "))) {
						clusters.set(j, (clusters.get(j) + " " + mst.to).trim());
					} else if (!mm.containsInArrayString(mst.from, clusters.get(j).split(" "))
							&& mm.containsInArrayString(mst.to, clusters.get(j).split(" "))) {
						clusters.set(j, (clusters.get(j) + " " + mst.from).trim());
					}
				}
			}
		}
		String twopoint = "";
		for (MSTStructure mst : topNDistance) {
			twopoint += mst.from + " " + mst.to + " ";
		}

		twopoint = twopoint.trim();
		String[] tp = twopoint.split(" ");
		
		for (String p : places) {
			if (!mm.containsInArrayString(p, tp)) {
				int ind = -1;
				int kk=0;
				for (String clust : clusters) {
					if(clust.isEmpty())
					{
						ind=kk;
						break;
					}
					kk++;
				}
				if(ind==-1)
				{
				clusters.add(p);
				}
				else
				{
				clusters.set(ind,p);	
				}
			}
		}
		int sanj=0;
		for (String clust : clusters) {
			System.out.println("K"+(sanj+1)+" : { "+clust.trim().replace(" ", ",")+" }");
			sanj++;
		}
	}
}
