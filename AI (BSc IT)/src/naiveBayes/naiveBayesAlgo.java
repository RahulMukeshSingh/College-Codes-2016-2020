package naiveBayes;

import java.util.ArrayList;

public class naiveBayesAlgo {

	public static void main(String[] args) {
		String datas = (new csvread()).csvreadnaiveBayes("E:\\simpleApproach.csv");
		String[] data = datas.split("@");
		String columnName = data[0];
		String values = data[1];
		int idIndex = Integer.valueOf(data[2]);
		
		String tuples = data[3];
		String columnIndex = data[4];
		String[] colIndex = columnIndex.split(",");
		naiveBayesMethods nbm = new naiveBayesMethods();
		String numericColumnIndex = nbm.getNumericColumnIndex(values, idIndex);
		String[] tup = tuples.split(" ");
		String[] colName = columnName.split(",");
		ArrayList<ArrayList<String>> rowandcol = nbm.getRowAndColumnIndex(values, columnIndex, numericColumnIndex);
		double[][] val = nbm.getValuesForClassification(values, columnIndex, numericColumnIndex).get(1);
		double[][] valNoProb = nbm.getValuesForClassification(values, columnIndex, numericColumnIndex).get(0);
		String[] output = nbm.getJustColumnValueFromWholeVal(values, values.split(" ")[0].split(",").length - 1);
		ArrayList<String> outputDistinct = nbm.getValofColumns(output);
		for (String od : outputDistinct) {
			System.out.print("\t" + od);
		}
		for (String od : outputDistinct) {
			System.out.print("\t" + "Prob_" + od);
		}
		int r = 0;
		System.out.println();
		int k = 0;
		for (ArrayList<String> rnc : rowandcol) {
			System.out.println(colName[k]);
			for (String rn : rnc) {
				System.out.print(rn + "\t|\t");
				for (double v : valNoProb[r]) {
					System.out.print(Math.round(v * 100D) / 100D + "\t");
				}
				for (double v : val[r]) {
					System.out.print(Math.round(v * 100D) / 100D + "\t");
				}
				System.out.println();
				r++;
			}
			System.out.println();
			k++;
		}

		System.out.println();

		double[] probOut = nbm.getOutputProbability(output);
		for (String t : tup) {
			String tupproboutput = nbm.getTupleClassified(columnIndex, numericColumnIndex, output, probOut, val, t,
					rowandcol);
			System.out.println(t.split(",")[idIndex]+" has "+tupproboutput);
		}

	}
}
