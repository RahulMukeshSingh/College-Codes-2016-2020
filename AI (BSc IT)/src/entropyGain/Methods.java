package entropyGain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Methods {
	public ArrayList<ArrayList<String>> readFromCsv(String location) {
		ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
		ArrayList<String> columnName = new ArrayList<String>();
		ArrayList<String> tuple = new ArrayList<String>();
		ArrayList<String> data = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(location));
			String row = "";
			int i = 0;
			while ((row = br.readLine()) != null) {
				if (i == 0) {
					columnName.add(row);
				} else {
					String[] cells = row.split(",");
					if (cells[cells.length - 1].equals("?")) {
						tuple.add(row);
					} else {
						data.add(row);
					}
				}
				i++;
			}
			datas.add(columnName);
			datas.add(data);
			datas.add(tuple);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datas;
	}

	public ArrayList<String> distinct(String[] column) {
		ArrayList<String> dist = new ArrayList<>();
		dist.add(column[0]);
		for (int i = 1; i < column.length; i++) {
			if (!dist.contains(column[i])) {
				dist.add(column[i]);
			}
		}

		return dist;
	}

	public String[] getColumn(String[][] data, int i) {
		String[] column = new String[data.length];
		for (int j = 0; j < data.length; j++) {
			column[j] = data[j][i];
		}
		return column;
	}

	public double[] getColumn(double[][] data, int i) {
		double[] column = new double[data.length];
		for (int j = 0; j < data.length; j++) {
			column[j] = data[j][i];
		}
		return column;
	}

	public double getTotalEntropy(String[][] data) {
		double entropyy = 0;
		String[] output = getColumn(data, data[0].length - 1);
		ArrayList<String> distOutput = distinct(output);

		double[] pandq = new double[distOutput.size()];
		int j = 0;
		for (String dout : distOutput) {
			int counter = 0;
			for (int i = 0; i < output.length; i++) {
				if (dout.equals(output[i])) {
					counter++;
				}
			}
			pandq[j] = counter;
			j++;
		}
		entropyy = entropy(pandq);
		return entropyy;
	}

	public double entropy(double[] pandq) {
		double entropy = 0;
		double sum = 0;
		for (int i = 0; i < pandq.length; i++) {
			sum += pandq[i];
		}
		for (int i = 0; i < pandq.length; i++) {

			if (pandq[i] != 0) {
				double val = pandq[i] / sum;
				entropy += (-val * (Math.log(val) / Math.log(2)));
			}
		}

		return entropy;
	}

	public double gain(double remainder, double totalEntropy) {
		double gainn = totalEntropy - remainder;
		return gainn;
	}

	public double remainder(double[][] pandq,String[][] data) {
		double rem = 0;
		for (int i = 0; i < pandq[0].length; i++) {
			double[] column=getColumn(pandq, i);
			double sum=0;
			for (int j = 0; j < column.length; j++) {
				sum+=column[j];
			}
			rem += (sum/data.length)*entropy(column);
		}
		return rem;
	}

	public void arrangement(String[][] data, String[] columnName) {
		double[] gain = new double[columnName.length - 2];
		double totalEntropy = getTotalEntropy(data);
		System.out.println("Total Entropy : "+totalEntropy);
		String[] output = getColumn(data, columnName.length - 1);
		ArrayList<String> distOut = distinct(output);
		for (int i = 1; i < columnName.length - 1; i++) {
			String[] column = getColumn(data, i);
			ArrayList<String> distCol = distinct(column);
			double[][] pandq = new double[distOut.size()][distCol.size()];
			for (int j = 0; j < distCol.size(); j++) {
				for (int j2 = 0; j2 < distOut.size(); j2++) {
					int counter = 0;
					for (int k = 0; k < data.length; k++) {
						if (output[k].equals(distOut.get(j2)) && column[k].equals(distCol.get(j))) {
							counter++;
						}
					}
					pandq[j2][j] = counter;
				}
			}
			double rem = remainder(pandq,data);
			System.out.println("Remainder of "+columnName[i]+" : "+rem);
			gain[i - 1] = gain(rem, totalEntropy);

		}
		for (int i = 1; i < columnName.length-1; i++) {

			System.out.println("Gain of "+columnName[i]+" : "+gain[i-1]);

		}

	}
 
}