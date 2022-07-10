package practs5;

import java.util.ArrayList;

public class Parse_Table {
	public Grammar_DS[] getRHS(int currIndex, ArrayList<String> terminals, ArrayList<ArrayList<String>> first,
			ArrayList<ArrayList<String>> follow, ArrayList<Grammar_DS> grammar) {
		Grammar_DS currProd = grammar.get(currIndex);
		Grammar_DS[] parseRow = new Grammar_DS[terminals.size() - 1];
		for (ArrayList<String> currProdRHS : currProd.rhs) {
			ArrayList<ArrayList<String>> rhs = new ArrayList<ArrayList<String>>();
			rhs.add(currProdRHS);
			String firstElement = currProdRHS.get(0);
			if (firstElement.equals("Epsilon")) {
				ArrayList<String> currFollow = follow.get(currIndex);
				for (String cf : currFollow) {
					parseRow[terminals.indexOf(cf)] = new Grammar_DS(currProd.nonTerminal, rhs);
				}
			} else if (terminals.contains(firstElement)) {
				parseRow[terminals.indexOf(firstElement)] = new Grammar_DS(currProd.nonTerminal, rhs);
			} else {
				ArrayList<String> firstOfFirstElement = first
						.get(new Follow().getNonTerminalIndex(firstElement, grammar));
				for (String fofe : firstOfFirstElement) {
					parseRow[terminals.indexOf(fofe)] = new Grammar_DS(currProd.nonTerminal, rhs);
				}
			}
		}
		return parseRow;
	}

	public Grammar_DS[][] parseTable(ArrayList<String> terminals, ArrayList<ArrayList<String>> first,
			ArrayList<ArrayList<String>> follow, ArrayList<Grammar_DS> grammar) {
		Grammar_DS[][] table = new Grammar_DS[grammar.size()][terminals.size() - 1];
		for (int currIndex = 0; currIndex < grammar.size(); currIndex++) {
			table[currIndex] = getRHS(currIndex, terminals, first, follow, grammar);
		}
		return table;
	}
}
