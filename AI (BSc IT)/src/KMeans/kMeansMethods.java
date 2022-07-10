package KMeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class kMeansMethods {
	public double[][] getDistance(ArrayList<String> value, ArrayList<String> means) {
		double dist = 0;
		double[][] distances = new double[means.size()][value.size()];

		for (int a = 0; a < means.size(); a++) {
			String[] meann = means.get(a).split(",");
			for (int b = 0; b < value.size(); b++) {
				String[] val = value.get(b).split(",");
				dist = 0;
				for (int i = 0; i < val.length; i++) {
					dist += Math.pow(Double.parseDouble(val[i]) - Double.parseDouble(meann[i]), 2);

				}
				dist = Math.round(Math.sqrt(dist) * 100D) / 100D;
				// System.out.println(dist+" "+a+" "+b);
				distances[a][b] = dist;
			}
		}

		return distances;

	}

	public ArrayList<kMeansStructure> classify(int idcolumn, int noOfKeys, String input) {
		ArrayList<kMeansStructure> classified = new ArrayList<kMeansStructure>();
		String[] withoutSpace = input.split(" ");
		ArrayList<String> means = getInitialMeans(noOfKeys, idcolumn, withoutSpace);
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		
		for (String withCommaLeft : withoutSpace) {
			String[] withoutComma = withCommaLeft.split(",");
			String value = "";
			for (int wc = 0; wc < withoutComma.length; wc++) {
				if (wc == idcolumn - 1) {
					id.add(withoutComma[wc]);
				} else {
					value += withoutComma[wc] + " ";
				}
			}
			values.add(value.trim().replace(" ", ","));
		}

		classified.add(new kMeansStructure(id, values, means));

		return classified;
	}

	public ArrayList<ArrayList<String>> getClusters(ArrayList<String> id, double[][] distance) {
		ArrayList<String> clusters = new ArrayList<String>();
		ArrayList<ArrayList<String>> clustersCollections = new ArrayList<ArrayList<String>>();
		ArrayList<String> clustersIndex = new ArrayList<String>();

		for (int i = 0; i < distance.length; i++) {
			clusters.add("");
			clustersIndex.add("");
		}
		for (int j = 0; j < distance[0].length; j++) {
			double min = distance[0][j];
			int minindex = 0;
			for (int i = 0; i < distance.length; i++) {
				if (distance[i][j] < min) {
					min = distance[i][j];
					minindex = i;
				}
			}
			clusters.set(minindex, clusters.get(minindex) + " " + id.get(j));
			clustersIndex.set(minindex, clustersIndex.get(minindex) + " " + String.valueOf(j));
		}
		for (int i = 0; i < distance.length; i++) {
			clusters.set(i, clusters.get(i).trim().replace(" ", ","));
			clustersIndex.set(i, clustersIndex.get(i).trim().replace(" ", ","));
		}
		clustersCollections.add(clusters);
		clustersCollections.add(clustersIndex);
		return clustersCollections;
	}

	public ArrayList<String> getMeans(ArrayList<String> clustersIndex, ArrayList<String> values) {

		ArrayList<String> means = new ArrayList<String>();
		double[][] mean = new double[clustersIndex.size()][values.get(0).split(",").length];
		for (int i = 0; i < clustersIndex.size(); i++) {
			means.add("");
		}
		int i = 0;
		for (String indexWithComma : clustersIndex) {
			String[] indexes = indexWithComma.split(",");
			for (String index : indexes) {
				String value = "";
				if (!index.isEmpty()) {
					value = values.get(Integer.valueOf(index));
				}
				String[] val = value.split(",");
				int j = 0;

				for (String v : val) {
					if (v.isEmpty()) {
						v = "0";
					}
					mean[i][j] += (Double.valueOf(v) / indexes.length);

					j++;
				}
			}
			i++;
		}
		int k = 0;
		for (double[] mn : mean) {
			for (double m : mn) {
				means.set(k, means.get(k) + " " + String.valueOf(Math.round(m * 100D) / 100D));
			}
			k++;
		}
		k = 0;
		for (String m : means) {
			means.set(k, m.trim().replace(" ", ","));
			k++;
		}
		return means;
	}

	public ArrayList<String> getInitialMeans(int noofkeys, int idIndex, String[] valuesWithoutSpace) {
		ArrayList<String> means = new ArrayList<String>();
		int col = valuesWithoutSpace[0].split(",").length - 1;
		int[] len = (new kMeansMethods()).getIndexForInitialMeansSortedArray(noofkeys, valuesWithoutSpace.length);
		double[][] arrayForSort = new double[valuesWithoutSpace.length][col];

		int l = 0;
		for (String values : valuesWithoutSpace) {
			String[] valuesWithoutComma = values.split(",");
			int m = 0;
			for (int j = 0; j < valuesWithoutComma.length; j++) {
				if (j != idIndex - 1) {
					arrayForSort[l][m] = Double.parseDouble(valuesWithoutComma[j]);
					m++;
				}
			}
			l++;
		}
		double[][] sortedArray = new double[col][valuesWithoutSpace.length];
		for (int p = 0; p < col; p++) {
			for (int q = 0; q < valuesWithoutSpace.length; q++) {
				sortedArray[p][q] = arrayForSort[q][p];
			}
		}
		for (int p = 0; p < col; p++) {
			Arrays.sort(sortedArray[p]);
		}

		for (int p = 0; p < noofkeys; p++) {
			String mean = "";
			for (int j = 0; j < sortedArray.length; j++) {
				mean += sortedArray[j][len[p]] + " ";
			}
			means.add(mean.trim().replace(" ", ","));
		}

		return means;
	}

	public void arrangement(String filelocation, int noofkeys) {
		String fromFile = (new csvread()).csvreadKmeans(filelocation);
		String[] input = fromFile.split("@");
		int k = noofkeys;
		kMeansMethods km = new kMeansMethods();
		ArrayList<kMeansStructure> kms = km.classify(Integer.valueOf(input[0]), k, input[1]);
		ArrayList<String> clusters = new ArrayList<String>();
		ArrayList<String> oldClusters = new ArrayList<String>();
		ArrayList<String> means = kms.get(0).means;
		do {
			oldClusters = clusters;
			double[][] distances = km.getDistance(kms.get(0).val, kms.get(0).means);
			int u = 0;
			System.out.print("                   ");
			for (String id : kms.get(0).id) {
				System.out.print(id + "     ");
			}
			System.out.println();
			for (double[] ds : distances) {
				System.out.print("m" + (u + 1) + "(" + means.get(u) + ") : ");

				for (double d : ds) {
					System.out.print(d + "    ");
				}
				System.out.println();
				u++;
			}
			ArrayList<ArrayList<String>> clustersCollection = km.getClusters(kms.get(0).id, distances);
			clusters = clustersCollection.get(0);
			int l = 0;
			for (String c : clusters) {
				System.out.println("k" + (l + 1) + " : {" + c + "}");
				l++;
			}
			means = km.getMeans(clustersCollection.get(1), kms.get(0).val);

			kms.get(0).means = means;
		} while (!clusters.equals(oldClusters));

	}

	public int arrayMax(int[] len) {
		int maxx = 0;
		for (int i : len) {
			if (i > maxx) {
				maxx = i;
			}
		}
		return maxx;
	}

	public int arrayMin(int[] len) {
		int minn = len[0];

		for (int i : len) {

			if (i < minn && i != 0) {
				minn = i;
			}
		}

		return minn;
	}

	public int[] getIndexForInitialMeansSortedArray(int noOfKey, int rowno) {
		int[] len = new int[noOfKey];
		int mid = rowno / 2;
		len[0] = mid;

		for (int i = 1; i < noOfKey; i++) {
			if (i % 2 == 0) {
				len[i] = arrayMax(len) + 1;
			} else {
				// System.out.println("min:"+min(len));
				len[i] = arrayMin(len) - 1;

			}
		}
		return len;
	}

}
