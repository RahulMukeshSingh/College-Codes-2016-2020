package practs3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Input {
	Scanner sc = new Scanner(System.in);

	public Grammar_DS getLL1DS(String grammar) {
		String[] gram = grammar.trim().split("->");
		String nonTerminal = gram[0].trim();
		String[] rhsAll = gram[1].trim().split("\\|");
		ArrayList<ArrayList<String>> rhs = new ArrayList<ArrayList<String>>();
		for (String rhsEach : rhsAll) {
			rhs.add(new ArrayList<String>(Arrays.asList(rhsEach.trim().split(" "))));
		}
		return new Grammar_DS(nonTerminal, rhs);
	}

	public ArrayList<Grammar_DS> getGrammar() {
		System.out.println("Enter the no. of Productions : ");
		int noOfProd = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the Grammar : ");
		ArrayList<Grammar_DS> grammar = new ArrayList<Grammar_DS>();
		for (int i = 0; i < noOfProd; i++) {
			grammar.add(getLL1DS(sc.nextLine()));
		}
		return grammar;
	}
}
