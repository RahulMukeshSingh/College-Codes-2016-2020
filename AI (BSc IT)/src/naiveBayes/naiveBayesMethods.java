package naiveBayes;

import java.util.ArrayList;
import java.util.Iterator;

import simpleApproach.simpleApproachMethods;

public class naiveBayesMethods {
	public ArrayList<String> getValofColumns(String[] in) {
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

	public String[] getJustColumnValueFromWholeVal(String values, int index) {
		String[] value = values.split(" ");
		String[] output = new String[value.length];
		int i = 0;
		for (String val : value) {
			String[] v = val.split(",");

			output[i] = v[index];

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
			if (isDouble(v[i]) && i != idIndex ) {
				out += i + " ";
			}

		}
		return out.trim();
	}

	public ArrayList<String> getContinousClass(double[] x) {
		double max = maxOfArray(x);
		double min = minOfArray(x);
		double height = Math.round((max - min) * 10D) / 100D;
		// System.out.println(height);
		ArrayList<String> arr = new ArrayList<String>();
		double last = min;
		arr.add(0.0 + "-" + Math.round(last * 100D) / 100D);
		for (int i = 1; i < 11; i++) {
			arr.add(Math.round(last * 100D) / 100D + "-" + Math.round((last + height) * 100D) / 100D);
			last = last + height;
		}
		arr.add("   >=" + Math.round(last * 100D) / 100D);
		return arr;

	}

	public double maxOfArray(double[] x) {
		double max = 0;
		for (double d : x) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	public double minOfArray(double[] x) {
		double min = x[0];
		for (double d : x) {
			if (d < min) {
				min = d;
			}
		}
		return min;
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

	public ArrayList<ArrayList<String>> getRowAndColumnIndex(String values, String columnIndex,
			String numericColumnIndex) {
		ArrayList<ArrayList<String>> rowandcolumnindex = new ArrayList<ArrayList<String>>();
		String[] colIndex = columnIndex.split(",");
		String[] numColIndex = numericColumnIndex.split(" ");
		for (int i = 0; i < colIndex.length; i++) {
			String[] x = getJustColumnValueFromWholeVal(values, Integer.valueOf(colIndex[i]));
			if (containsInArrayString(colIndex[i], numColIndex)) {

				double[] y = new double[x.length];
				for (int j = 0; j < x.length; j++) {
					y[j] = Double.valueOf(x[j]);

				}
				rowandcolumnindex.add(getContinousClass(y));
			} else {
				rowandcolumnindex.add(getValofColumns(x));
			}
		}

		return rowandcolumnindex;
	}

	public ArrayList<double[][]> getValuesForClassification(String values, String columnIndex,
			String numericColumnIndex) {
		ArrayList<ArrayList<String>> rowandcol = getRowAndColumnIndex(values, columnIndex, numericColumnIndex);

		String[] colIndex = columnIndex.split(",");
		String[] numColIndex = numericColumnIndex.split(" ");
		int row = 0;
		for (int i = 0; i < rowandcol.size(); i++) {
			row += rowandcol.get(i).size();

		}
		String[] output = getJustColumnValueFromWholeVal(values, values.split(" ")[0].split(",").length - 1);
		ArrayList<String> outputDistinct = getValofColumns(output);
		int col = outputDistinct.size();
		double[][] val = new double[row][col];
		double[][] probval = new double[row][col];
		int cc = 0;
		for (String od : outputDistinct) {
			int rr = 0;
			int coll = 0;
			for (ArrayList<String> rnc : rowandcol) {

				for (String r : rnc) {
					int counter = 0;

					if (containsInArrayString(colIndex[coll], numColIndex)) {

						String[] colAttr = getJustColumnValueFromWholeVal(values, Integer.valueOf(colIndex[coll]));

						for (int i = 0; i < colAttr.length; i++) {
							boolean con = false;
							if (!r.contains(">=")) {

								String[] limits = r.split("-");
								if (Double.valueOf(colAttr[i]) >= Double.valueOf(limits[0])
										&& Double.valueOf(colAttr[i]) < Double.valueOf(limits[1])) {
									con = true;
								}
							} else {
								if (r.contains(">=")) {
									String[] limits = r.split(">=");
									if (Double.valueOf(colAttr[i]) >= Double.valueOf(limits[1])) {
										con = true;
									}
								}
							}
							if (od.equals(output[i]) && con) {
								counter++;
							}

						}

					} else {
						String[] colAttr = getJustColumnValueFromWholeVal(values, Integer.valueOf(colIndex[coll]));
						for (int i = 0; i < colAttr.length; i++) {
							if (od.equals(output[i]) && r.equals(colAttr[i])) {
								counter++;
							}

						}

					}
					val[rr][cc] = counter;
					rr++;
				}
				coll++;
			}
			cc++;
		}

		ArrayList<double[][]> valuess = new ArrayList<double[][]>();
		valuess.add(val);
		for (int i = 0; i < val[0].length; i++) {

			int start = 0;
			for (ArrayList<String> rnc : rowandcol) {

				int len = start + rnc.size();
				int sum = 0;
				for (int j = start; j < len; j++) {
					sum += val[j][i];
				}
				for (int j = start; j < len; j++) {
					probval[j][i] = val[j][i] / sum;
				}
				start = len;
			}
		}
		valuess.add(probval);
		return valuess;
	}

	public double[] getOutputProbability(String[] output) {
		ArrayList<String> outputDistinct = getValofColumns(output);
		double[] probOutput = new double[outputDistinct.size()];
		int j = 0;
		for (String od : outputDistinct) {
			int cnt = 0;
			for (int i = 0; i < output.length; i++) {
				if (od.equals(output[i])) {
					cnt++;
				}

			}

			// System.out.println((double)cnt/output.length);
			probOutput[j] = (double) cnt / output.length;

			j++;
		}

		return probOutput;
	}

	public String getTupleClassified(String columnIndex, String numericColumnIndex, String[] output, double[] probOut,
			double[][] val, String tuple, ArrayList<ArrayList<String>> rowandcol) {
		String[] colIndex = columnIndex.split(",");
		String[] numColIndex = numericColumnIndex.split(" ");

		String[] tup = tuple.split(",");
		int i = 0;
		ArrayList<String> outputDistinct = getValofColumns(output);
		double[] tupprobOutput = new double[outputDistinct.size()];
		int newTupIndex = 0;
		for (String od : outputDistinct) {
			int row = 0;
			int tupIndex = 0;
			tupprobOutput[newTupIndex] = 1;
			for (ArrayList<String> rnc : rowandcol) {

				for (String r : rnc) {
					if (containsInArrayString(colIndex[tupIndex], numColIndex)) {

						if (!r.contains(">=")) {

							String[] limits = r.split("-");
							if (Double.valueOf(tup[Integer.valueOf(colIndex[tupIndex])]) >= Double.valueOf(limits[0])
									&& Double.valueOf(tup[Integer.valueOf(colIndex[tupIndex])]) < Double
											.valueOf(limits[1])) {
								tupprobOutput[newTupIndex] *= val[row][newTupIndex];
							}
						} else {
							if (r.contains(">=")) {
								String[] limits = r.split(">=");
								if (Double.valueOf(tup[Integer.valueOf(colIndex[tupIndex])]) >= Double
										.valueOf(limits[1])) {
									tupprobOutput[newTupIndex] *= val[row][newTupIndex];
								}
							}
						}

					} else {
						if (r.equals(tup[Integer.valueOf(colIndex[tupIndex])])) {
							tupprobOutput[newTupIndex] *= val[row][newTupIndex];
						}
					}
					row++;
				}
				tupIndex++;
			}
			newTupIndex++;
		}
		double probTuple = 0;
		for (int j = 0; j < tupprobOutput.length; j++) {
			probTuple += tupprobOutput[j] * probOut[j];

		}

		double[] finalProb = new double[tupprobOutput.length];
		for (int j = 0; j < finalProb.length; j++) {
			finalProb[j] = (tupprobOutput[j] * probOut[j]) / probTuple;

		}
		double max = finalProb[0];
		int maxIndex = 0;
		int ii = 0;
		for (double d : finalProb) {
			if (d > max) {
				max = d;
				maxIndex = ii;
			}
			ii++;
		}
		return outputDistinct.get(maxIndex);
	}
}
