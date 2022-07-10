package practs3;

import java.util.ArrayList;

public class Grammar_DS {
	String nonTerminal;
	ArrayList<ArrayList<String>> rhs;

	public Grammar_DS() {

	}

	public Grammar_DS(String nonTerminal, ArrayList<ArrayList<String>> rhs) {
		this.nonTerminal = nonTerminal;
		this.rhs = rhs;
	}

	public String displayList(ArrayList<String> list) {
		String result = "";
		for (String l : list) {
			result += l;
		}
		return result;
	}

	public String display() {
		String rhsList = displayList(rhs.get(0));
		for (int index = 1; index < rhs.size(); index++) {
			rhsList += " | " + displayList(rhs.get(index));
		}
		return nonTerminal + " -> " + rhsList;
	}
}
