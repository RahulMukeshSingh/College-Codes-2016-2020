package decisionTree;

import java.util.ArrayList;

class IDthreeMethods {
	public ArrayList<String> getValofNodes(String[] in) {
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

	public double IofS(String[] output) {
		IDthreeMethods idt = new IDthreeMethods();
		ArrayList<String> s = idt.getValofNodes(output);
		ArrayList<Double> ss = new ArrayList<Double>();
		double flag;
		for (int i = 0; i < s.size(); i++) {
			flag = 0;
			for (int j = 0; j < output.length; j++) {
				if (s.get(i).equals(output[j])) {
					flag++;
				}

			}

			ss.add(flag);
		}
		double p = (ss.get(0)) / (ss.get(0) + ss.get(1));
		double n = (ss.get(1)) / (ss.get(0) + ss.get(1));
		double iofs = idt.iforanything(p, n);
		return iofs;
	}

	public double iforanything(double p, double n) {

		double iforr = 0;
		double plog = 0, nlog = 0;
		if (p > 0) {
			plog = Math.log(p) / Math.log(2);
		}
		if (n > 0) {
			nlog = Math.log(n) / Math.log(2);
		}

		iforr = -p * (plog) - n * (nlog);
		return iforr;

	}

	public double[] gain(String[][] input, String[] output) {
		double p, n;
		IDthreeMethods idt = new IDthreeMethods();
		double[] gains = new double[input[0].length];
		String[] numattr = new String[input.length];
		ArrayList<String> attrname;
		ArrayList<String> out;
		double flag;
		// double attrnodesval;
		double[][] ynnodes;
		double rem, summ;
		for (int j = 0; j < input[0].length; j++) {

			for (int i = 0; i < input.length; i++) {
				numattr[i] = input[i][j];
			}

			attrname = idt.getValofNodes(numattr);
			out = idt.getValofNodes(output);
			ynnodes = new double[attrname.size()][out.size()];

			for (int u = 0; u < attrname.size(); u++) {

				for (int v = 0; v < out.size(); v++) {
					flag = 0;
					for (int w = 0; w < output.length; w++) {
						if (out.get(v).equals(output[w]) && attrname.get(u).equals(numattr[w])) {
							flag++;
						}
					}
					ynnodes[u][v] = flag;
				}
			}
			rem = 0;
			for (int u = 0; u < attrname.size(); u++) {

				summ = 0;
				for (int v = 0; v < out.size(); v++) {

					summ += ynnodes[u][v];

				}

				p = ynnodes[u][0] / summ;

				n = ynnodes[u][1] / summ;

				rem += (summ / output.length) * idt.iforanything(p, n);

			}

			gains[j] = idt.IofS(output) - rem;
		}
		return gains;
	}

	public String[] getAttributeSorted(String[][] input, String[] output, String[] attr) {
		double[] gains = gain(input, output);
		int[] attrIndex = new int[gains.length];
		for (int j = 0; j < gains.length; j++) {
			attrIndex[j] = j;
		}

		double tempGainsIndex;
		int tempAttrIndex;
		for (int i = 0; i < gains.length; i++) {
			for (int j = i; j < gains.length; j++) {
				if (gains[i] < gains[j]) {
					tempGainsIndex = gains[j];
					gains[j] = gains[i];
					gains[i] = tempGainsIndex;
					tempAttrIndex = attrIndex[j];
					attrIndex[j] = attrIndex[i];
					attrIndex[i] = tempAttrIndex;
				}
			}
		}

		String[] attrSorted = new String[gains.length];
		for (int i = 0; i < gains.length; i++) {
			attrSorted[i] = attr[attrIndex[i]];
		}

		return attrSorted;
	}

	public ArrayList<decisionTreeStructure> createTreeStructure(String[][] input, String[] output, String[] attr,
			String[] gains) {
		ArrayList<decisionTreeStructure> tree = new ArrayList<decisionTreeStructure>();
		String remainingRowIndex = "";
		for (int i = 0; i < output.length; i++) {
			remainingRowIndex += i + " ";

		}
		remainingRowIndex = remainingRowIndex.trim();
		ArrayList<nodes> childs = new ArrayList<nodes>();
		;
		for (int i = 0; i < gains.length; i++) {
			ArrayList<nodes> parent = new ArrayList<nodes>();

			if (i == 0) {
				parent.add(new nodes(i, gains[i], null, remainingRowIndex));
			} else {
				for (nodes par : childs) {
					if (containsInArrayString(par.values, gains)) {
						parent.add(par);
					}
				}
			}
			for (nodes parentNodes : parent) {

				String[][] matForClass = getMatrixForClassification(input, parentNodes.values, attr, output,
						parentNodes.remainingRowIndex);
				ArrayList<String> values = new ArrayList<String>();
				childs = new ArrayList<nodes>();
				ArrayList<String> colDistinct = getValofNodes(
						getParticularColumnIndex(input, parentNodes.values, attr));
				if ((i + 1) < gains.length) {
					values = getValuesForNodes(matForClass, output, gains[i + 1]);
				} else {
					values = getValuesForNodes(matForClass, output, "Not_Classified");
				}

				for (int j = 0; j < colDistinct.size(); j++) {
					remainingRowIndex = matForClass[0][j] + " " + matForClass[1][j];
					childs.add(new nodes(i + 1, values.get(j), colDistinct.get(j), remainingRowIndex));
				}
				tree.add(new decisionTreeStructure(parentNodes, childs));
			}
		}
		return tree;
	}

	public String[] getParticularColumnIndex(String[][] input, String colName, String[] attr) {
		int colIndex = 0;
		String[] col = new String[input.length];
		for (int i = 0; i < attr.length; i++) {
			if (attr[i].equals(colName)) {
				colIndex = i;
			}

		}
		for (int i = 0; i < input.length; i++) {
			col[i] = input[i][colIndex];

		}
		return col;
	}

	public String[][] getMatrixForClassification(String[][] input, String colName, String[] attr, String[] output,
			String rowIndex) {
		String[] col = getParticularColumnIndex(input, colName, attr);
		ArrayList<String> outputDistinct = getValofNodes(output);
		ArrayList<String> colDistinct = getValofNodes(col);
		String[][] matForClass = new String[outputDistinct.size()][colDistinct.size()];
		String[] remIndexes = rowIndex.split(" ");

		for (int j = 0; j < outputDistinct.size(); j++) {
			for (int k = 0; k < colDistinct.size(); k++) {
				matForClass[j][k] = "";
				for (int i = 0; i < col.length; i++) {
					if (containsInArray(i, remIndexes)) {
						if (output[i].equals(outputDistinct.get(j)) && col[i].equals(colDistinct.get(k))) {
							matForClass[j][k] += i + " ";
						}
					}
				}
				matForClass[j][k] = matForClass[j][k].trim();
			}

		}
		return matForClass;
	}

	public boolean containsInArray(int value, String[] col) {
		boolean result = false;
		for (String c : col) {
			if (value == Integer.valueOf(c)) {
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

	public boolean[] isClassified(String[][] matForClass) {
		boolean[] classified = new boolean[matForClass[0].length];
		for (int i = 0; i < classified.length; i++) {
			classified[i] = false;
		}
		for (int i = 0; i < matForClass[0].length; i++) {
			for (int j = 0; j < matForClass.length; j++) {
				if (matForClass[j][i].isEmpty()) {
					classified[i] = true;
					break;
				}
			}
		}
		return classified;
	}

	public ArrayList<String> getValuesForNodes(String[][] matForClass, String[] output, String gain) {
		ArrayList<String> values = new ArrayList<String>();
		String val = "";
		ArrayList<String> outVal = getValofNodes(output);
		boolean[] classified = isClassified(matForClass);
		for (int i = 0; i < matForClass[0].length; i++) {
			val = "";
			if (classified[i]) {
				for (int j = 0; j < matForClass.length; j++) {
					if (!matForClass[j][i].isEmpty()) {
						val = outVal.get(j);
					}
				}
			} else {
				if (gain.equals("Not_Classified")) {
					
						if (matForClass[0][i].split(" ").length > matForClass[1][i].split(" ").length) {
							val = outVal.get(0);
						}
						else
						{
							val = outVal.get(1);
						}
					
				} else {
					val = gain;
				}
			}
			values.add(val);
		}
		return values;
	}

	public int getColumnIndex(String colName, String[] attr) {
		int colIndex = 0;

		for (int i = 0; i < attr.length; i++) {
			if (attr[i].equals(colName)) {
				colIndex = i;
			}

		}

		return colIndex;
	}

	public String getClassificationforTuple(String id,ArrayList<decisionTreeStructure> cts, String tuple, String[] gains,
			String[] attr) {
		String answer = "";
		String[] tup = tuple.split(",");
		String[] tupArrangedAccordingToGain = new String[gains.length];
		for (int i = 0; i < tupArrangedAccordingToGain.length; i++) {
			tupArrangedAccordingToGain[i] = tup[getColumnIndex(gains[i], attr)];
			
		}
		System.out.println();
		int i = 0;
		answer = gains[0];
		for (decisionTreeStructure dts : cts) {

			for (nodes n : dts.childs) {

				if (tupArrangedAccordingToGain[i].equals(n.associatedParentValue) && dts.parent.values.equals(answer)) {
					answer = n.values;
				}

			}
			i++;
		}
		return id +" has "+answer;
	}
}