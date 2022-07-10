package practs1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

class DFAStateDS {
	String stateName;
	ArrayList<String> eClosure;

	public DFAStateDS(String stateName, ArrayList<String> eClosure) {
		this.stateName = stateName;
		this.eClosure = eClosure;
	}
}

public class NFAToDFAEpsilon {
	public String getMove(ArrayList<String> eClosure, ArrayList<String> nfaStates, ArrayList<String[]> nfaTable,
			int inputIndex) {
		String move = "";
		for (String eStates : eClosure) {
			String[] nfaRow = nfaTable.get(nfaStates.indexOf(eStates));
			if (!nfaRow[inputIndex].equalsIgnoreCase("N/A")) {
				move += nfaRow[inputIndex] + ",";
			}
		}
		return move.substring(0, move.length() - 1).trim();
	}

	public ArrayList<String> eClosure(String closures, ArrayList<String> nfaStates, ArrayList<String[]> nfaTable) {
		ArrayList<String> closure = new ArrayList<String>(Arrays.asList(closures.trim().split(",")));
		for (int index = 0; index < closure.size(); index++) {
			String[] nfaRow = nfaTable.get(nfaStates.indexOf(closure.get(index).trim()));
			String epsilonCell = nfaRow[nfaRow.length - 1].trim();
			if (!epsilonCell.equalsIgnoreCase("N/A")) {
				String[] epsilonStates = epsilonCell.split(",");
				for (String eState : epsilonStates) {
					if (!closure.contains(eState.trim())) {
						closure.add(eState.trim());
					}
				}
			}
		}
		Collections.sort(closure);
		return closure;
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

	public String getNameOfState(ArrayList<String> eClosure, ArrayList<DFAStateDS> dfaStateDetails) {
		for (DFAStateDS stateDetails : dfaStateDetails) {
			if (stateDetails.eClosure.equals(eClosure)) {
				return stateDetails.stateName;
			}
		}
		return null;
	}

	public ArrayList<String> getEClosureOfState(ArrayList<DFAStateDS> dfaStateDetails, String stateName) {
		for (DFAStateDS stateDetails : dfaStateDetails) {
			if (stateDetails.stateName.equalsIgnoreCase(stateName)) {
				return stateDetails.eClosure;
			}
		}
		return null;
	}


	public void thompsonSubsetConstruction(ArrayList<String[]> nfaTable, ArrayList<String> nfaStates,
			ArrayList<String> inputs) {
		ArrayList<String[]> dfaTable = new ArrayList<String[]>();
		ArrayList<String> dfaStates = new ArrayList<String>();
		ArrayList<DFAStateDS> dfaStateDetails = new ArrayList<DFAStateDS>();
		ArrayList<String> unvisitedState = new ArrayList<String>();
		String stateName = "S0";
		ArrayList<String> eClosure = eClosure(nfaStates.get(0), nfaStates, nfaTable);
		dfaStateDetails.add(new DFAStateDS(stateName, eClosure));
		dfaStates.add(stateName);
		unvisitedState.add(stateName);
		while (unvisitedState.size() > 0) {
			String row = "";
			String currentStateName = unvisitedState.get(0);
			ArrayList<String> currentStateEClosure = getEClosureOfState(dfaStateDetails, currentStateName);
			for (int inputIndex = 0; inputIndex < inputs.size() - 1; inputIndex++) {
				String move = getMove(currentStateEClosure, nfaStates, nfaTable, inputIndex);
				eClosure = eClosure(move, nfaStates, nfaTable);
				if (getNameOfState(eClosure, dfaStateDetails) == null) {
					stateName = "S" + dfaStateDetails.size();
					dfaStateDetails.add(new DFAStateDS(stateName, eClosure));
					dfaStates.add(stateName);
					unvisitedState.add(stateName);
				} else {
					stateName = getNameOfState(eClosure, dfaStateDetails);
				}
				row += " " + stateName;
			}
			dfaTable.add(row.trim().split(" "));
			unvisitedState.remove(0);
		}
		System.out.println("\nDFA :-");
		inputs.remove(inputs.size() - 1);
		display(dfaTable, dfaStates, inputs);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the no of states : ");
		int noStates = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the no of inputs (Except Epsilon): ");
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
		inputs.add("Epsilon");
		ArrayList<String[]> nfaTable = new ArrayList<String[]>();
		System.out.println("State Transition : ");
		for (int i = 0; i < noStates; i++) {
			nfaTable.add(sc.nextLine().split(" "));
		}
		NFAToDFAEpsilon ntde = new NFAToDFAEpsilon();
		System.out.println("NFA :-");
		ntde.display(nfaTable, nfaStates, inputs);
		ntde.thompsonSubsetConstruction(nfaTable, nfaStates, inputs);
	}
}
