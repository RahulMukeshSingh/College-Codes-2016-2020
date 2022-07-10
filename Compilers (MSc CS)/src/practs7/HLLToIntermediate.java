package practs7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import practs6.Representation;
import practs6.ThreeAddressCode;

public class HLLToIntermediate {
	public ArrayList<Representation> results;
	int varName;

	public HLLToIntermediate() {
		results = new ArrayList<Representation>();
		varName = 0;
	}

	private String getCondition(String input, String startKeyword, String endKeyword) {
		int start = input.indexOf(startKeyword) + startKeyword.length();
		int end = input.indexOf(endKeyword);
		String condition = input.substring(start, end);
		return condition.trim();
	}

	private ArrayList<String> getBlock(ArrayList<String> hll, int startIndex, String endBlock) {
		ArrayList<String> result = new ArrayList<String>();
		for (int index = startIndex; !(hll.get(index).contains(endBlock)); index++) {
			result.add(hll.get(index).trim());
		}
		return result;
	}

	private void tacLine(String line) {
		ThreeAddressCode tac = new ThreeAddressCode(line);
		tac.varName = varName;
		tac.tacStatement();
		varName = tac.varName;
		results.addAll(tac.results);
	}

	private void convert(ArrayList<String> hll, int index) {
		if (index < hll.size()) {
			String line = hll.get(index).trim();
			if (line.startsWith("if")) {
				String condition = getCondition(line, "if", "then");
				tacLine(condition);
				String condTempVar = "T" + varName;
				results.add(new Representation("jmpf", condTempVar, "", ""));
				int elseJumpIndex = results.size() - 1;
				index++;
				ArrayList<String> ifBlock = getBlock(hll, index, "else");
				index = index + ifBlock.size() + 1;
				convert(ifBlock, 0);
				results.add(new Representation("jmp", "", "", ""));
				int outJumpIndex = results.size() - 1;
				results.get(elseJumpIndex).result = String.valueOf(results.size());
				ArrayList<String> elseBlock = getBlock(hll, index, "endif");
				index = index + elseBlock.size() + 1;
				convert(elseBlock, 0);
				results.get(outJumpIndex).result = String.valueOf(results.size());
			} else if (line.startsWith("while")) {
				int loopIndex = results.size();
				String condition = getCondition(line, "while", "do");
				tacLine(condition);
				String condTempVar = "T" + varName;
				results.add(new Representation("jmpf", condTempVar, "", ""));
				int outJumpIndex = results.size() - 1;
				index++;
				ArrayList<String> loopBlock = getBlock(hll, index, "endloop");
				index = index + loopBlock.size() + 1;
				convert(loopBlock, 0);
				results.add(new Representation("jmp", "", "", String.valueOf(loopIndex)));
				results.get(outJumpIndex).result = String.valueOf(results.size());
			} else {
				tacLine(line);
				index++;
			}
			convert(hll, index);
		}
	}
	
	public void getIntermediateCode(ArrayList<String> hll){
		convert(hll, 0);
		results.add(new Representation("end", "", "", ""));
	}
	
	public ArrayList<String> readInput() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
		ArrayList<String> result = new ArrayList<String>();
		String line = "";
		while ((line = br.readLine()) != null) {
			result.add(line);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		HLLToIntermediate hllToInter = new HLLToIntermediate();
		ArrayList<String> hll = hllToInter.readInput();
		hllToInter.getIntermediateCode(hll);
		int index = 0;
		for (Representation rep : hllToInter.results) {
			System.out
					.println(index + " : " + rep.operator + " " + rep.operand1 + "," + rep.operand2 + "," + rep.result);
			index++;
		}
	}

}
