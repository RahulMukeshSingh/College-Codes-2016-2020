package practs5;

import java.util.ArrayList;

public class First {
	public Grammar_DS getGrammar(String nonTerminal, ArrayList<Grammar_DS> grammar) {
		for (Grammar_DS gram : grammar) {
			if (gram.nonTerminal.equals(nonTerminal)) {
				return gram;
			}
		}
		return null;
	}

	public ArrayList<String> getFirst(String rootNonTerminal, ArrayList<String> firstList, ArrayList<String> terminals,
			ArrayList<Grammar_DS> grammar) {
		ArrayList<ArrayList<String>> rhs = getGrammar(rootNonTerminal, grammar).rhs;
		for (ArrayList<String> rhsEach : rhs) {
			String firstElement = rhsEach.get(0);
			if (terminals.contains(firstElement)) {
				if (!firstList.contains(firstElement)) {
					firstList.add(firstElement);
				}
			} else {
				getFirst(getGrammar(firstElement, grammar).nonTerminal, firstList, terminals, grammar);
			}
		}
		return firstList;
	}

	public ArrayList<ArrayList<String>> first(ArrayList<String> terminals, ArrayList<Grammar_DS> grammar) {
		ArrayList<ArrayList<String>> firstNonTerminal = new ArrayList<ArrayList<String>>();
		for (Grammar_DS gram : grammar) {
			ArrayList<String> firstList = new ArrayList<String>();
			firstNonTerminal.add(getFirst(gram.nonTerminal, firstList, terminals, grammar));
		}
		return firstNonTerminal;
	}

}
