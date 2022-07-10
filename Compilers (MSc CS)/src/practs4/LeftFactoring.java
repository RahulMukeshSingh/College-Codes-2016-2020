package practs4;

import java.util.ArrayList;

public class LeftFactoring {

	public ArrayList<String> getDistinctFirstLetter(Grammar_DS production) {
		ArrayList<String> distinctFirstLetters = new ArrayList<String>();
		for (ArrayList<String> eachRHS : production.rhs) {
			String firstLetter = eachRHS.get(0);
			if (!distinctFirstLetters.contains(firstLetter)) {
				distinctFirstLetters.add(firstLetter);
			}
		}
		return distinctFirstLetters;
	}

	public ArrayList<ArrayList<ArrayList<String>>> getCommonList(Grammar_DS production) {
		ArrayList<ArrayList<ArrayList<String>>> commonList = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> common = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> others = new ArrayList<ArrayList<String>>();
		ArrayList<String> distinctFirstLetters = getDistinctFirstLetter(production);
		int maxCount = 1;
		for (String distinctFirstLetter : distinctFirstLetters) {
			ArrayList<ArrayList<String>> currCommon = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> currOthers = new ArrayList<ArrayList<String>>();
			for (ArrayList<String> eachRHS : production.rhs) {
				String firstLetter = eachRHS.get(0);
				if (distinctFirstLetter.equals(firstLetter)) {
					currCommon.add(eachRHS);
				} else {
					currOthers.add(eachRHS);
				}
			}
			int currMaxCount = currCommon.size();
			if (currMaxCount > maxCount) {
				maxCount = currMaxCount;
				common = currCommon;
				others = currOthers;
			}
		}
		if (common.isEmpty()) {
			others = production.rhs;
		}
		commonList.add(common);
		commonList.add(others);
		return commonList;
	}

	public String getNewNonTerminal(String nonTerminal, ArrayList<Grammar_DS> grammar) {
		int maxSize = 0;
		String maxNonTerminal = "";
		for (Grammar_DS gram : grammar) {
			String currNonTerminal = gram.nonTerminal;
			int currMaxSize = currNonTerminal.length();
			if (currNonTerminal.startsWith(nonTerminal)) {
				if (currMaxSize > maxSize) {
					maxSize = currMaxSize;
					maxNonTerminal = currNonTerminal;
				}
			}
		}
		return maxNonTerminal + "\'";
	}

	public ArrayList<Grammar_DS> getLeftFactorList(String nonTerminal, ArrayList<ArrayList<String>> common,
			ArrayList<ArrayList<String>> others, ArrayList<Grammar_DS> grammar) {
		ArrayList<Grammar_DS> modifiedProduction = new ArrayList<Grammar_DS>();
		String commonLetter = common.get(0).get(0);
		String newNonTerminal = getNewNonTerminal(nonTerminal, grammar);
		ArrayList<ArrayList<String>> nonTerminalRHS = new ArrayList<ArrayList<String>>();
		ArrayList<String> eachRHS = new ArrayList<String>();
		eachRHS.add(commonLetter);
		eachRHS.add(newNonTerminal);
		nonTerminalRHS.add(eachRHS);
		nonTerminalRHS.addAll(others);
		modifiedProduction.add(new Grammar_DS(nonTerminal, nonTerminalRHS));
		ArrayList<ArrayList<String>> newNonTerminalRHS = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> com : common) {
			com.remove(0);
			if (com.isEmpty()) {
				eachRHS = new ArrayList<String>();
				eachRHS.add("Epsilon");
				newNonTerminalRHS.add(eachRHS);
			} else {
				newNonTerminalRHS.add(com);
			}
		}
		modifiedProduction.add(new Grammar_DS(newNonTerminal, newNonTerminalRHS));
		return modifiedProduction;
	}

	public void leftFactor(ArrayList<Grammar_DS> grammar) {
		int index = 0;
		while (index < grammar.size()) {
			Grammar_DS production = grammar.get(index);
			ArrayList<ArrayList<ArrayList<String>>> commonList = getCommonList(production);
			ArrayList<ArrayList<String>> common = commonList.get(0);
			ArrayList<ArrayList<String>> others = commonList.get(1);
			if (common.isEmpty()) {
				index++;
			} else {
				ArrayList<Grammar_DS> newProduction = getLeftFactorList(production.nonTerminal, common, others,
						grammar);
				grammar.remove(index);
				grammar.addAll(index, newProduction);
			}
		}
	}

	public static void main(String[] args) {
		Input in = new Input();
		LeftFactoring lf = new LeftFactoring();
		ArrayList<Grammar_DS> grammar = in.getGrammar();
		lf.leftFactor(grammar);
		System.out.println("\nAfter Left Factoring : ");
		for (Grammar_DS gram : grammar) {
			System.out.println(gram.display());
		}
	}

}
