package decisionTree;

import java.util.*;

public class DecisionTree {

	public static void main(String[] args) {
		ArrayList<ArrayList<String>> idanddata=(new csvread()).csvreadDecisionTree("E:\\ID32.csv");
		ArrayList<String> id =idanddata.get(0);
		ArrayList<String> data =idanddata.get(1) ;
		String[] attr = new String[data.get(0).split(",").length - 1];
		int dataLen=data.size() - 1;
		String tuples="";
		String tupleidindex="";
		int ti=0;
		for (String lines : data) {
			
			String[] line = lines.split(",");
			if(line[line.length-1].equals("?"))
			{
				dataLen--;
				tuples+=lines+" ";
				tupleidindex+=ti+" ";
			}
			ti++;
		}
		tuples=tuples.trim();
		tupleidindex=tupleidindex.trim().replace(" ", ",");
		String[][] input = new String[dataLen][data.get(0).split(",").length - 1];
		String[] output = new String[dataLen];
        
		for (int i = 0; i <= dataLen; i++) {
			String[] line = data.get(i).split(",");
			if (i == 0) {
				for (int j = 0; j < line.length - 1; j++) {
					attr[j] = line[j];
				}

			} else {

				for (int j = 0; j < line.length; j++) {
					if (j != line.length - 1) {
						input[i - 1][j] = line[j];
					} else {
						output[i - 1] = line[j];
					}
				}
			}
		}
		
		IDthreeMethods idt = new IDthreeMethods();
		double[] gaa=idt.gain(input, output);
		String[] g = idt.getAttributeSorted(input, output, attr);
		for (String ga : g) {
			System.out.println(ga);
		}
		for (double ga : gaa) {
			System.out.println(ga);
		}
		System.out.println("============");
		ArrayList<decisionTreeStructure> cts = idt.createTreeStructure(input, output, attr, g);
		System.out.println(cts.get(0).parent.id + " " + cts.get(0).parent.values + " " + cts.get(0).parent.associatedParentValue + " "
				+ cts.get(0).parent.remainingRowIndex);
		for (decisionTreeStructure dts : cts) {
			System.out.println(dts.parent.values);
			for (nodes n : dts.childs) {
				System.out.println(n.id + " " + n.values + " " + n.associatedParentValue + " " + n.remainingRowIndex);

			}
			
		}
		System.out.println("============");
	String[] tuple=tuples.split(" ");	
	String[] tupleIndexes=tupleidindex.split(",");
	ti=0;
	for (String tup : tuple) {
	System.out.println(idt.getClassificationforTuple(tupleIndexes[ti],cts, tup, g, attr));	
		ti++;
	}
	}
}
