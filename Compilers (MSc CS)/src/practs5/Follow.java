package practs5;

import java.util.ArrayList;

public class Follow {
	public int getNonTerminalIndex(String nonTerminal, ArrayList<Grammar_DS> grammar) {
		for (int index = 0; index < grammar.size(); index++) {
			Grammar_DS gram = grammar.get(index);
			if (gram.nonTerminal.equals(nonTerminal)) {
				return index;
			}
		}
		return -1;
	}

	public void distinctAppend(String nonTerminal, ArrayList<String> append, ArrayList<String> followList,
			ArrayList<ArrayList<String>> followNonTerminal, ArrayList<Grammar_DS> grammar) {
		for (String app : append) {
			if (!followList.contains(app)) {
				if (app.equals("Epsilon")) {
					int index = getNonTerminalIndex(nonTerminal, grammar);
					distinctAppend(nonTerminal, followNonTerminal.get(index), followList, followNonTerminal, grammar);
				} else {
					followList.add(app);
				}
			}
		}
	}

	public ArrayList<ArrayList<String>> follow(ArrayList<String> terminals, ArrayList<ArrayList<String>> first,
			ArrayList<Grammar_DS> grammar) {
		ArrayList<ArrayList<String>> followNonTerminal = new ArrayList<ArrayList<String>>();
		boolean flag = true;
		for (int i = 0; i < first.size(); i++) {
			ArrayList<String> followList = new ArrayList<String>();
			if (flag) {
				followList.add("$");
				flag = false;
			}
			followNonTerminal.add(followList);
		}
		for (int i = 0; i < grammar.size(); i++) {
			Grammar_DS gram = grammar.get(i);
			ArrayList<String> followList = followNonTerminal.get(i);
			for (int j = 0; j < grammar.size(); j++) {
				Grammar_DS inGram = grammar.get(j);
				for (ArrayList<String> rhsEach : inGram.rhs) {
					if (rhsEach.contains(gram.nonTerminal)) {
						int followIndex = rhsEach.indexOf(gram.nonTerminal) + 1;
						if (followIndex < rhsEach.size()) {
							String followElement = rhsEach.get(followIndex);
							if (terminals.contains(followElement)) {
								if (!followList.contains(followElement)) {
									followList.add(followElement);
								}
							} else {
								int index = getNonTerminalIndex(followElement, grammar);
								distinctAppend(inGram.nonTerminal, first.get(index), followList, followNonTerminal,
										grammar);
							}
						} else {
							distinctAppend(inGram.nonTerminal, followNonTerminal.get(j), followList, followNonTerminal,
									grammar);
						}
					}
				}
			}
		}

		return followNonTerminal;
	}

}
