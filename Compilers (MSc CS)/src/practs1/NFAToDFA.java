package practs1;

import java.util.ArrayList;
import java.util.Scanner;

public class NFAToDFA {
	public String getTransitionState(ArrayList<String[]> nfaTable, ArrayList<String> nfaStates, String states,
			int inputIndex) {
		String state = "";
		for (int i = 0; i < states.length(); i++) {
			String name = String.valueOf(states.charAt(i));
			int stateIndex = nfaStates.indexOf(name);
			if (stateIndex >= 0) {
				String[] stateArray = nfaTable.get(stateIndex);
				if (!stateArray[inputIndex].equalsIgnoreCase("N/A")) {
					state += stateArray[inputIndex];
				}
			}
		}
		if (state.equals("")) {
			return "Z";
		}
		return state.replace(",", "").replace(" ", "");
	}

	public void display(ArrayList<String[]> table, ArrayList<String> states, ArrayList<String> inputs) {
		System.out.print("\t");
		for (String in : inputs) {
			System.out.print(in + "\t");
		}
		System.out.println();
		for (int index = 0; index < states.size(); index++) {
			System.out.print(states.get(index) + "\t");
			for (String state : table.get(index)) {
				System.out.print(state + "\t");
			}
			System.out.println();
		}
	}

	public void subsetConstruction(ArrayList<String[]> nfaTable, ArrayList<String> nfaStates,
			ArrayList<String> inputs) {
		ArrayList<String[]> dfaTable = new ArrayList<String[]>();
		ArrayList<String> dfaStates = new ArrayList<String>();
		ArrayList<String> unvisitedState = new ArrayList<String>();
		unvisitedState.add(nfaStates.get(0));
		dfaStates.add(nfaStates.get(0));
		while (unvisitedState.size() > 0) {
			String newRowState = unvisitedState.get(0);
			String[] row = new String[inputs.size()];
			for (int inputIndex = 0; inputIndex < inputs.size(); inputIndex++) {
				row[inputIndex] = getTransitionState(nfaTable, nfaStates, newRowState, inputIndex);
				if (!dfaStates.contains(row[inputIndex])) {
					dfaStates.add(row[inputIndex]);
					unvisitedState.add(row[inputIndex]);
				}
			}
			dfaTable.add(row);
			unvisitedState.remove(0);
		}
		System.out.println("\nDFA :-");
		display(dfaTable, dfaStates, inputs);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the no of states : ");
		int noStates = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the no of inputs : ");
		int noInputs = sc.nextInt();
		sc.nextLine();
		ArrayList<String> nfaStates = new ArrayList<String>();
		System.out.println("States : ");
		for (int i = 0; i < noStates; i++) {
			nfaStates.add(sc.nextLine());
		}
		ArrayList<String> inputs = new ArrayList<String>();
		System.out.println("Inputs : ");
		for (int i = 0; i < noInputs; i++) {
			inputs.add(sc.nextLine());
		}
		ArrayList<String[]> nfaTable = new ArrayList<String[]>();
		System.out.println("State Transition : ");
		for (int i = 0; i < noStates; i++) {
			nfaTable.add(sc.nextLine().split(" "));
		}
		NFAToDFA ntd = new NFAToDFA();
		System.out.println("NFA :-");
		ntd.display(nfaTable, nfaStates, inputs);
		ntd.subsetConstruction(nfaTable, nfaStates, inputs);
	}

}
