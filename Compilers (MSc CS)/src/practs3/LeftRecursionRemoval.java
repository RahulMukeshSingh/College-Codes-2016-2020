package practs3;

import java.util.ArrayList;

public class LeftRecursionRemoval {

	public boolean isLeftRecursive(Grammar_DS production) {
		for (ArrayList<String> rhs : production.rhs) {
			String firstElement = rhs.get(0);
			if (firstElement.equals(production.nonTerminal)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<ArrayList<ArrayList<String>>> getAlphaBetaList(Grammar_DS production) {
		ArrayList<ArrayList<ArrayList<String>>> alphaBetaList = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> alpha = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> beta = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> rhs : production.rhs) {
			rhs.add(production.nonTerminal + "\'");
			if (rhs.contains("Epsilon")) {
				rhs.remove(rhs.indexOf("Epsilon"));
			}
			String firstElement = rhs.get(0);
			if (firstElement.equals(production.nonTerminal)) {
				rhs.remove(0);
				alpha.add(rhs);
			} else {
				beta.add(rhs);
			}
		}
		ArrayList<String> epsilon = new ArrayList<String>();
		epsilon.add("Epsilon");
		alpha.add(epsilon);
		alphaBetaList.add(alpha);
		alphaBetaList.add(beta);
		return alphaBetaList;
	}

	public ArrayList<Grammar_DS> removeLeftRecursion(ArrayList<Grammar_DS> grammar) {
		ArrayList<Grammar_DS> modifiedGrammar = new ArrayList<Grammar_DS>();
		for (Grammar_DS production : grammar) {
			if (isLeftRecursive(production)) {
				ArrayList<ArrayList<ArrayList<String>>> alphaBetaList = getAlphaBetaList(production);
				ArrayList<ArrayList<String>> alpha = alphaBetaList.get(0);
				ArrayList<ArrayList<String>> beta = alphaBetaList.get(1);
				String nonTerminal = production.nonTerminal;
				modifiedGrammar.add(new Grammar_DS(nonTerminal, beta));
				modifiedGrammar.add(new Grammar_DS(nonTerminal + "\'", alpha));
			} else {
				modifiedGrammar.add(production);
			}
		}
		return modifiedGrammar;
	}

	public static void main(String[] args) {
		Input in = new Input();
		LeftRecursionRemoval lrr = new LeftRecursionRemoval();
		ArrayList<Grammar_DS> grammar = in.getGrammar();
		ArrayList<Grammar_DS> modifiedGrammar = lrr.removeLeftRecursion(grammar);
		System.out.println("\nAfter Removing Left Recursion : ");
		for (Grammar_DS modGram : modifiedGrammar) {
			System.out.println(modGram.display());
		}
	}

}

