package simpleApproach;

import java.util.ArrayList;

public class simpleApproachMethods {
	public ArrayList<String> getValofOutput(String[] in) {
		ArrayList<String> out = new ArrayList<String>();
		out.add(in[0]);
		int flag = 0;
		for (int i = 1; i < in.length; i++) {
			flag = 0;
			for (int j = 0; j < out.size(); j++) {

				if (out.get(j).equalsIgnoreCase(in[i])) {
					flag++;
				}
			}
			if (flag == 0) {
				out.add(in[i]);
			}
		}
		return out;
	}

	public String[] getJustOutputFromWholeVal(String values) {
		String[] value = values.split(" ");
		String[] output = new String[value.length];
		int i = 0;
		for (String val : value) {
			String[] v = val.split(",");

			output[i] = v[v.length - 1];

			i++;
		}

		return output;
	}

	public boolean isDouble(String in) {
		try {
			Double.parseDouble(in);
			return true;
		} catch (NumberFormatException n) {
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	public String getNumericColumnIndex(String values, int idIndex) {
		
		String[] value = values.split(" ");
		String out = "";

		String[] v = value[0].split(",");
		for (int i = 0; i < v.length - 1; i++) {
			if (isDouble(v[i]) && i != idIndex - 1) {
				out += i + " ";
			}

		}
		return out.trim();
	}

	public String getCentre(String values, String[] NumericColumnIndex, ArrayList<String> valOfOutput,
			String[] output) {
		String cent = "";
		ArrayList<Double> centers = new ArrayList<Double>();
		String[] value = values.split(" ");
		for (int i = 0; i < NumericColumnIndex.length; i++) {
			centers.add((double) 0);
		}
		int u = 0;
		for (int i = 0; i < valOfOutput.size(); i++) {
			u = 0;
			String temp = "";
			for (int z = 0; z < NumericColumnIndex.length; z++) {
				centers.set(z, (double) 0);
			}
			for (int j = 0; j < output.length; j++) {
				if (output[j].equals(valOfOutput.get(i))) {
					String[] val = value[j].split(",");
					for (int k = 0; k < NumericColumnIndex.length; k++) {
						centers.set(k, centers.get(k) + Double.valueOf(val[Integer.valueOf(NumericColumnIndex[k])]));
					}
					u++;
				}
			}
			for (double center : centers) {
				temp += Math.round((center / u) * 100D) / 100D + " ";
			}
			cent += temp.trim().replace(" ", ",") + " ";
		}
		return cent.trim();
	}

	public ArrayList<Double> getDistance(String tuples, String[] NumericColumnIndex, String centers) {
		ArrayList<Double> distances = new ArrayList<Double>();
		String[] tuple = tuples.split(",");
		String[] center = centers.split(" ");
		for (String cent : center) {
			double dist = 0;
			String[] c = cent.split(",");
			for (int i = 0; i < c.length; i++) {
				dist += Math.abs(Double.valueOf(c[i]) - Double.valueOf(tuple[Integer.valueOf(NumericColumnIndex[i])]));

			}
			distances.add(Math.round(dist * 100D) / 100D);
		}
		return distances;
	}

	public String getTheMinimumDistanceCenter(ArrayList<String> ValofOutput, ArrayList<Double> Distance) {
		double min = Distance.get(0);
		int minIndex = 0;
		for (int i = 1; i < Distance.size(); i++) {
			if (Distance.get(i) < min) {
				min = Distance.get(i);
				minIndex = i;
			}

		}

		return ValofOutput.get(minIndex);
	}

	public void arrangement(String fileLocation) {
		String allData = (new csvread()).csvreadSimpleApproach(fileLocation);
		String[] data = allData.split("@");
		String values = data[0];
		int idIndex = Integer.valueOf(data[1]);
		
		String tuples = data[2];
		String[] tuple = tuples.split(" ");
		simpleApproachMethods sam = new simpleApproachMethods();
		String[] output = sam.getJustOutputFromWholeVal(values);
		ArrayList<String> valOfOutput = sam.getValofOutput(output);
		String[] NumericColumnIndex = sam.getNumericColumnIndex(values, idIndex).split(" ");
		String centers = sam.getCentre(values, NumericColumnIndex, valOfOutput, output);
		for (String tup : tuple) {
			ArrayList<Double> distances = sam.getDistance(tup, NumericColumnIndex, centers);
			String[] t = tup.split(",");
			System.out.println(t[idIndex-1] + " -> " + sam.getTheMinimumDistanceCenter(valOfOutput, distances));
		}

	}
}
