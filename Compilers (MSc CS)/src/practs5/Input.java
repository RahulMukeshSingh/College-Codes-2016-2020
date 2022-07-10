package practs5;

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

	public ArrayList<String> getTerminals() {
		System.out.println("Enter the no. of Terminals : ");
		int noOfTerm = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the Terminals : ");
		ArrayList<String> terminals = new ArrayList<String>();
		for (int i = 0; i < noOfTerm; i++) {
			terminals.add(sc.nextLine());
		}
		terminals.add("$");
		terminals.add("Epsilon");
		return terminals;
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
