package practs5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LL1Parser {
	public ArrayList<String> reverseAdd(ArrayList<String> parsingData, ArrayList<String> stack) {
		stack.remove((stack.size() - 1));
		for (int i = (parsingData.size() - 1); i >= 0; i--) {
			String toAdd = parsingData.get(i);
			if (!toAdd.equals("Epsilon")) {
				stack.add(parsingData.get(i));
			}
		}
		return stack;
	}

	public void LL1Parsing(String inputString, ArrayList<String> terminals, Grammar_DS[][] parseTable,
			ArrayList<Grammar_DS> grammar) {
		Grammar_DS disp = new Grammar_DS();
		Follow follow = new Follow();
		ArrayList<String> stack = new ArrayList<String>();
		stack.add("$");
		stack.add(grammar.get(0).nonTerminal);
		String[] inputArray = inputString.split(" ");
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(inputArray));
		input.add("$");
		boolean flag = true;
		while (flag) {
			System.out.print(disp.displayList(stack) + "\t" + disp.displayList(input));
			String output;
			String stackLastElement = stack.get((stack.size() - 1));
			String inputFirstElement = input.get(0);
			if (stackLastElement.equals(inputFirstElement)) {
				stack.remove((stack.size() - 1));
				input.remove(0);
				output = "";
				if (stack.isEmpty() && input.isEmpty()) {
					flag = false;
					output = "Accepted";
				}
			} else {
				if (terminals.contains(stackLastElement)) {
					flag = false;
					output = "Not Accepted";
				} else {
					int row = follow.getNonTerminalIndex(stackLastElement, grammar);
					int col = terminals.indexOf(inputFirstElement);
					Grammar_DS cell = parseTable[row][col];
					if (cell == null) {
						flag = false;
						output = "Not Accepted";
					} else {
						stack = reverseAdd(cell.rhs.get(0), stack);
						output = cell.display();
					}
				}
			}
			System.out.print("\t" + output + "\n");
		}
	}

	public static void main(String[] args) {
		Input in = new Input();
		First fir = new First();
		Follow fol = new Follow();
		Parse_Table parTable = new Parse_Table();
		LL1Parser ll1P = new LL1Parser();
		ArrayList<String> terminals = in.getTerminals();
		ArrayList<Grammar_DS> grammar = in.getGrammar();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Input : ");
		String inputString = sc.nextLine();
		ArrayList<ArrayList<String>> first = fir.first(terminals, grammar);
		ArrayList<ArrayList<String>> follow = fol.follow(terminals, first, grammar);
		System.out.println("First and Follow : ");
		System.out.println("NonTerminal \t\t First \t\t Follow");
		System.out.println(
				"---------------------------------------------------------------------------------------------------");
		for (int i = 0; i < grammar.size(); i++) {
			String nonTerminal = grammar.get(i).nonTerminal;
			String firstTerminal = first.get(i).toString();
			String followTerminal = follow.get(i).toString();
			System.out.println(nonTerminal + " \t\t " + firstTerminal + " \t\t " + followTerminal);
		}
		System.out.println("\nParsing Table : ");
		Grammar_DS[][] parseTable = parTable.parseTable(terminals, first, follow, grammar);
		for (int i = 0; i < (terminals.size() - 1); i++) {
			System.out.print(" \t\t " + terminals.get(i));
		}
		System.out.println(
				"\n---------------------------------------------------------------------------------------------------");
		for (int i = 0; i < parseTable.length; i++) {
			System.out.print(grammar.get(i).nonTerminal + " \t\t");
			for (int j = 0; j < parseTable[i].length; j++) {
				if (parseTable[i][j] != null) {
					System.out.print(parseTable[i][j].display() + " \t");
				} else {
					System.out.print("null \t\t");
				}
			}

			System.out.println();
		}
		System.out.println("\nLL(1) Parser : ");
		System.out.print("Stack \t Input \t Output");
		System.out.println(
				"\n---------------------------------------------------------------------------------------------------");
		ll1P.LL1Parsing(inputString, terminals, parseTable, grammar);
	}

}
