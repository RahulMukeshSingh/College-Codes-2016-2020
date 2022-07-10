package practs8;

import java.util.ArrayList;

import practs6.Representation;
import practs7.HLLToIntermediate;

public class DAG {

	public void printDAG(ArrayList<Representation> intermediate) {
		System.out.println("DAG for above Intermediate Code : ");
		System.out.println("Node : NextNode");
		int index = 0;
		for (Representation rep : intermediate) {
			String nextNode = "";
			if (index == (intermediate.size() - 1)) {
				nextNode = "No Node";
			} else if (rep.operator.trim().equals("jmpf")) {
				nextNode = String.valueOf(index + 1) + "," + rep.result;
			} else if (rep.operator.trim().equals("jmp")) {
				nextNode = rep.result;
			} else {
				nextNode = String.valueOf(index + 1);
			}
			System.out.println(index + " : " + nextNode);
			index++;
		}
	}

	public static void main(String[] args) throws Exception {
		HLLToIntermediate hllToInter = new HLLToIntermediate();
		ArrayList<String> hll = hllToInter.readInput();
		hllToInter.getIntermediateCode(hll);
		ArrayList<Representation> intermediate = hllToInter.results;
		int index = 0;
		System.out.println("Intermediate Code for input.txt : ");
		System.out.println("LineNo : Operator, Operand1, Operand2, Result");
		for (Representation rep : hllToInter.results) {
			System.out
					.println(index + " : " + rep.operator + " " + rep.operand1 + "," + rep.operand2 + "," + rep.result);
			index++;
		}
		DAG dag = new DAG();
		dag.printDAG(intermediate);
	}

}
