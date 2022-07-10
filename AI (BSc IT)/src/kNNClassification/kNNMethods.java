package kNNClassification;

import java.util.ArrayList;

import simpleApproach.simpleApproachMethods;

public class kNNMethods {
	public double[][] getNumericColumnValueFromWholeVal(String[][] values, int[] indexNumeric) {
		double[][] output = new double[values.length][indexNumeric.length];
		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < output[i].length; j++) {
				output[i][j] = Double.valueOf(values[i][indexNumeric[j]]);
			}
		}
		return output;
	}

	public double[] getNumericColumnValueFromSingleTuple(String[] tuples, int[] indexNumeric) {
		double[] output = new double[indexNumeric.length];
		for (int i = 0; i < output.length; i++) {
			output[i] = Double.valueOf(tuples[indexNumeric[i]]);
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

	public int[] getNumericColumnIndex(String[][] values, int idIndex) {
		String output = "";
		String[] v = values[0];

		for (int i = 0; i < v.length - 1; i++) {
			if (isDouble(v[i]) && i != idIndex) {
				output += i + " ";
			}

		}
		String[] out = output.trim().split(" ");
		int[] index = new int[out.length];
		for (int i = 0; i < index.length; i++) {
			index[i] = Integer.valueOf(out[i]);
		}
		return index;
	}

	public double[] getDistance(double[][] valueNumeric, double[] tupleNumeric) {
		double[] distances = new double[valueNumeric.length];
		for (int i = 0; i < valueNumeric.length; i++) {
			distances[i] = 0;
			for (int j = 0; j < valueNumeric[i].length; j++) {
				distances[i] += Math.abs(valueNumeric[i][j] - tupleNumeric[j]);

			}
		}
		return distances;
	}

	public ArrayList<kNNStructure> getTupleWithDistance(String[][] values, int idIndex, String[] tuples) {
		ArrayList<kNNStructure> datas = new ArrayList<kNNStructure>();
		int[] indexNumeric = getNumericColumnIndex(values, idIndex);
		double[][] valueNumeric = getNumericColumnValueFromWholeVal(values, indexNumeric);
		double[] tupleNumeric = getNumericColumnValueFromSingleTuple(tuples, indexNumeric);
		double[] distances = getDistance(valueNumeric, tupleNumeric);
		for (int i = 0; i < values.length; i++) {
			datas.add(new kNNStructure(values[i][idIndex], values[i][values[i].length - 1], distances[i]));
		}
		return datas;
	}

	public String getTupleClassified(ArrayList<kNNStructure> valuesWithDistance) {
		int K = (int) Math.sqrt(valuesWithDistance.size());
		kNNStructure[] valuesForKNumbers = new kNNStructure[K];
		for (int i = 0; i < valuesForKNumbers.length; i++) {
			valuesForKNumbers[i] = valuesWithDistance.get(i);
		}
		for (int i = K; i < valuesWithDistance.size(); i++) {
			for (int j = 0; j < valuesForKNumbers.length; j++) {
				if (valuesForKNumbers[j].distance > valuesWithDistance.get(i).distance) {
					valuesForKNumbers[j] = valuesWithDistance.get(i);
					break;
				}
			}
		}
		String[] outputforKNumbers = new String[K];
		for (int i = 0; i < outputforKNumbers.length; i++) {
			outputforKNumbers[i] = valuesForKNumbers[i].output;
		}
		return getMaxOccurance(outputforKNumbers);
	}

	public String getMaxOccurance(String[] outputforKNumbers) {
		int maxCount = 0;
		String out = "";
		for (int i = 0; i < outputforKNumbers.length; i++) {
			int count = 0;
			for (int j = 0; j < outputforKNumbers.length; j++) {
				if (outputforKNumbers[i].equalsIgnoreCase(outputforKNumbers[j])) {
					count++;
				}
			}
			if (count > maxCount) {
				maxCount = count;
				out = outputforKNumbers[i];
			}
		}
		return out;
	}
	public void arrangement(String fileLocation)
	{
		ArrayList<String> data = (new csvReadForKNN()).readFromCSVKNN("E:\\doctor.csv");
		int idIndex = Integer.parseInt(data.get(0));
		String[] attr = data.get(1).split(",");
		String[][] values = new String[data.get(2).split(" ").length][];
		for (int i = 0; i < values.length; i++) {
			values[i] = (data.get(2).split(" "))[i].split(",");
		}
		String[][] tuples = new String[data.get(3).split(" ").length][];
		for (int i = 0; i < tuples.length; i++) {
			tuples[i] = (data.get(3).split(" "))[i].split(",");
		}
		
		for (int i = 0; i < tuples.length; i++) {
			ArrayList<kNNStructure> valuesWithDistance=getTupleWithDistance(values, idIndex, tuples[i]);
			System.out.println(tuples[i][idIndex]+" ============> "+getTupleClassified(valuesWithDistance));	
		}
			
	}
}
