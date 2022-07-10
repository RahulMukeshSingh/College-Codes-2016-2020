package practs6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ThreeAddressCode {
	private ArrayList<String> inputs;
	public int varName;
	private ArrayList<String> relational, assignment;
	public ArrayList<Representation> results;
	HashMap<String, String> unaryOpr, binaryOpr;

	public ThreeAddressCode(String input) {
		this.inputs = new ArrayList<String>(Arrays.asList(input.split(" ")));
		varName = 0;
		relational = new ArrayList<String>();
		relational.add("%");
		relational.add("<");
		relational.add(">");
		relational.add("<=");
		relational.add(">=");
		relational.add("==");
		relational.add("!=");
		assignment = new ArrayList<String>();
		assignment.add("=");
		unaryOpr = new HashMap<String, String>();
		unaryOpr.put("-", "UMINUS");
		unaryOpr.put("&", "moveaddr");
		unaryOpr.put("*", "movecont");
		binaryOpr = new HashMap<String, String>();
		binaryOpr.put("+", "add");
		binaryOpr.put("-", "sub");
		binaryOpr.put("*", "mul");
		binaryOpr.put("/", "div");
		binaryOpr.put("=", "mov");
		binaryOpr.put("%", "mod");
		binaryOpr.put("<", "lt");
		binaryOpr.put(">", "gt");
		binaryOpr.put("<=", "lte");
		binaryOpr.put(">=", "gte");
		binaryOpr.put("==", "eq");
		binaryOpr.put("!=", "ne");
		results = new ArrayList<Representation>();
	}

	private String newTempVariable() {
		varName++;
		return "T" + varName;
	}

	private void replaceList(int fromIndex, int toIndex, String tempVariable) {
		for (int index = fromIndex; index <= toIndex; index++) {
			inputs.remove(fromIndex);
		}
		if (tempVariable != null) {
			inputs.add(fromIndex, tempVariable);
		}
	}

	private int mulDivOpr() {
		for (int index = 0; index < inputs.size(); index++) {
			String nextElement = inputs.get(index);
			if (nextElement.equals("*") || nextElement.equals("/")) {
				return index;
			}
		}
		return -1;
	}

	private int addSubOpr() {
		for (int index = 0; index < inputs.size(); index++) {
			String nextElement = inputs.get(index);
			if (nextElement.equals("+") || nextElement.equals("-")) {
				return index;
			}
		}
		return -1;
	}

	private int relationalOpr() {
		for (int index = 0; index < inputs.size(); index++) {
			String nextElement = inputs.get(index);
			if (relational.contains(nextElement)) {
				return index;
			}
		}
		return -1;
	}

	private int assignmentOpr() {
		for (int index = 0; index < inputs.size(); index++) {
			String nextElement = inputs.get(index);
			if (assignment.contains(nextElement)) {
				return index;
			}
		}
		return -1;
	}

	private void unaryOprProcedure() {
		for (int index = 0; index < inputs.size(); index++) {
			String element = inputs.get(index);
			String startChar = String.valueOf(element.charAt(0));
			if (unaryOpr.containsKey(startChar) && element.length() > 1) {
				String tempVariable = newTempVariable();
				results.add(new Representation(unaryOpr.get(startChar), element.substring(1), "",
						tempVariable));
				replaceList(index, index, tempVariable);
			}
		}
	}

	public void binaryOprProcedure(int index) {
		String tempVariable = newTempVariable();
		results.add(new Representation(binaryOpr.get(inputs.get(index)), inputs.get(index - 1), inputs.get(index + 1),
				tempVariable));
		replaceList((index - 1), (index + 1), tempVariable);
	}

	public void tacStatement() {
		unaryOprProcedure();
		int index = -1;
		while ((index = relationalOpr()) != -1) {
			binaryOprProcedure(index);
		}

		while ((index = mulDivOpr()) != -1) {
			binaryOprProcedure(index);
		}

		while ((index = addSubOpr()) != -1) {
			binaryOprProcedure(index);
		}

		while ((index = assignmentOpr()) != -1) {
			results.add(new Representation(binaryOpr.get(inputs.get(index)), inputs.get(index + 1), "",
					inputs.get(index - 1)));
			replaceList((index - 1), (index + 1), null);
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input : ");
		ThreeAddressCode tac = new ThreeAddressCode(sc.nextLine());
		tac.tacStatement();
		for (Representation rep : tac.results) {
			System.out.println(rep.operator + " " + rep.operand1 + "," + rep.operand2 + "," + rep.result);
		}
	}

}
