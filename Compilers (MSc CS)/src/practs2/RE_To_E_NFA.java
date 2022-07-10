package practs2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class State_DS {
	String stateName;
	ArrayList<ArrayList<String>> transition;

	public State_DS(String stateName, ArrayList<ArrayList<String>> transition) {
		this.stateName = stateName;
		this.transition = transition;
	}

}

public class RE_To_E_NFA {
	private ArrayList<State_DS> e_NFATable;
	private int stateName, size;
	private ArrayList<String> inputs;

	public RE_To_E_NFA(ArrayList<String> inputs) {
		this.inputs = inputs;
		stateName = 1;
		size = inputs.size();
		e_NFATable = new ArrayList<State_DS>();
	}

	public ArrayList<String> getBracketREElements(int fromIndex, ArrayList<String> reElements) {
		ArrayList<String> subREElements = new ArrayList<String>();
		int openBracket = 1;
		for (int index = (fromIndex + 1); openBracket != 0; index++) {
			String element = reElements.get(index);
			if (element.equals("(")) {
				openBracket++;
			} else if (element.equals(")")) {
				openBracket--;
			}
			if (openBracket != 0) {
				subREElements.add(element);
			}

		}
		return subREElements;
	}

	public ArrayList<String> getNextREElements(int fromIndex, ArrayList<String> reElements) {
		ArrayList<String> subREElements = new ArrayList<String>();
		String element = reElements.get(fromIndex);
		if (element.equals("(")) {
			subREElements.add("(");
			subREElements.addAll(getBracketREElements(fromIndex, reElements));
			subREElements.add(")");
		} else {
			subREElements.add(element);
		}
		int currIndex = fromIndex + subREElements.size();
		if (currIndex < reElements.size()) {
			String nextElement = reElements.get(currIndex);
			if (nextElement.equals("*")) {
				subREElements.add("*");
			}
		}
		return subREElements;
	}

	public State_DS getNewRow() {
		stateName++;
		ArrayList<ArrayList<String>> state = new ArrayList<ArrayList<String>>();
		for (int index = 0; index < size; index++) {
			ArrayList<String> stateTransition = new ArrayList<String>();
			stateTransition.add("N/A");
			state.add(stateTransition);
		}
		return new State_DS(String.valueOf(stateName), state);
	}

	public void appendList(String element, ArrayList<String> stateTransition) {
		if (!element.equals("N/A")) {
			if (stateTransition.get(0).equals("N/A")) {
				stateTransition.set(0, element);
			} else {
				if (!stateTransition.contains(element)) {
					stateTransition.add(element);
				}
			}
		}
	}

	public ArrayList<State_DS> getNewElementTable(String element, ArrayList<State_DS> curr_E_NFATable) {
		State_DS startState = getNewRow();
		int inIndex = inputs.indexOf(element);
		ArrayList<String> stateTransition = startState.transition.get(inIndex);
		appendList(String.valueOf((stateName + 1)), stateTransition);
		curr_E_NFATable.add(startState);
		curr_E_NFATable.add(getNewRow());
		return curr_E_NFATable;
	}

	public ArrayList<State_DS> getStarTable(ArrayList<State_DS> curr_E_NFATable) {
		State_DS oldStartState = curr_E_NFATable.get(0);
		State_DS oldEndState = curr_E_NFATable.get(curr_E_NFATable.size() - 1);
		ArrayList<String> oldEnd_E_Transition = oldEndState.transition.get(size - 1);
		appendList(oldStartState.stateName, oldEnd_E_Transition);
		appendList(String.valueOf(stateName + 1), oldEnd_E_Transition);
		State_DS newEndState = getNewRow();
		curr_E_NFATable.add(newEndState);

		State_DS newStartState = getNewRow();
		ArrayList<String> newStart_E_Transition = newStartState.transition.get(size - 1);
		appendList(oldStartState.stateName, newStart_E_Transition);
		appendList(newEndState.stateName, newStart_E_Transition);
		curr_E_NFATable.add(0, newStartState);
		return curr_E_NFATable;
	}

	public String decrementString(String stateName) {
		return String.valueOf((Integer.parseInt(stateName.trim()) - 1));
	}

	public void decrementStateName(ArrayList<State_DS> next_E_NFATable) {
		for (State_DS states : next_E_NFATable) {
			states.stateName = decrementString(states.stateName);
			for (ArrayList<String> transition : states.transition) {
				for (int index = 0; index < transition.size(); index++) {
					String trans = transition.get(index);
					if (!trans.equals("N/A")) {
						transition.set(index, decrementString(trans));
					}
				}
			}
		}
	}

	public ArrayList<State_DS> getConcatTable(ArrayList<State_DS> next_E_NFATable,
			ArrayList<State_DS> curr_E_NFATable) {
		decrementStateName(next_E_NFATable);
		ArrayList<ArrayList<String>> currEndTransitions = curr_E_NFATable.get(curr_E_NFATable.size() - 1).transition;
		ArrayList<ArrayList<String>> nextStartTransitions = next_E_NFATable.get(0).transition;
		for (int index = 0; index < size; index++) {
			ArrayList<String> currEndTransition = currEndTransitions.get(index);
			ArrayList<String> nextStartTransition = nextStartTransitions.get(index);
			for (String cet : nextStartTransition) {
				appendList(cet, currEndTransition);
			}
		}
		stateName--;
		next_E_NFATable.remove(0);
		curr_E_NFATable.addAll(next_E_NFATable);
		return curr_E_NFATable;
	}

	public ArrayList<State_DS> getOrTable(ArrayList<State_DS> next_E_NFATable, ArrayList<State_DS> curr_E_NFATable) {
		State_DS oldStartState1 = curr_E_NFATable.get(0);
		State_DS oldStartState2 = next_E_NFATable.get(0);
		State_DS oldEndState1 = curr_E_NFATable.get(curr_E_NFATable.size() - 1);
		State_DS oldEndState2 = next_E_NFATable.get(next_E_NFATable.size() - 1);

		appendList(String.valueOf(stateName + 1), oldEndState1.transition.get(size - 1));
		appendList(String.valueOf(stateName + 1), oldEndState2.transition.get(size - 1));
		State_DS newEndState = getNewRow();

		State_DS newStartState = getNewRow();
		appendList(oldStartState1.stateName, newStartState.transition.get(size - 1));
		appendList(oldStartState2.stateName, newStartState.transition.get(size - 1));

		curr_E_NFATable.addAll(next_E_NFATable);
		curr_E_NFATable.add(0, newStartState);
		curr_E_NFATable.add(newEndState);
		return curr_E_NFATable;
	}

	public void reToE_NFA(int index, ArrayList<State_DS> curr_E_NFATable, ArrayList<String> reElements) {
		if (index < reElements.size()) {
			String element = reElements.get(index);
			if (element.equals("(")) {
				ArrayList<String> subREElements = getBracketREElements(index, reElements);
				reToE_NFA(0, new ArrayList<State_DS>(), subREElements);
				curr_E_NFATable = this.e_NFATable;
				index = (index + subREElements.size() + 2);
			} else if (element.equals("*")) {
				curr_E_NFATable = getStarTable(curr_E_NFATable);
				index++;
			} else if (element.equals(".")) {
				index++;
				ArrayList<String> subREElements = getNextREElements(index, reElements);
				reToE_NFA(0, new ArrayList<State_DS>(), subREElements);
				ArrayList<State_DS> next_E_NFATable = this.e_NFATable;
				curr_E_NFATable = getConcatTable(next_E_NFATable, curr_E_NFATable);
				index = index + subREElements.size();
			} else if (element.equals("|")) {
				index++;
				ArrayList<String> subREElements = getNextREElements(index, reElements);
				reToE_NFA(0, new ArrayList<State_DS>(), subREElements);
				ArrayList<State_DS> next_E_NFATable = this.e_NFATable;
				curr_E_NFATable = getOrTable(next_E_NFATable, curr_E_NFATable);
				index = index + subREElements.size();
			} else {
				curr_E_NFATable = getNewElementTable(element, curr_E_NFATable);
				index++;
			}
			reToE_NFA(index, curr_E_NFATable, reElements);
		} else {
			this.e_NFATable = curr_E_NFATable;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter The Regular Expression : ");
		String re = sc.nextLine();
		String[] reElement = re.trim().split("|");
		ArrayList<String> reElements = new ArrayList<String>(Arrays.asList(reElement));
		ArrayList<String> inputs = new ArrayList<String>();
		System.out.println("Enter the no of inputs (Except Epsilon): ");
		int noInputs = sc.nextInt();
		sc.nextLine();
		System.out.println("Inputs : ");
		for (int i = 0; i < noInputs; i++) {
			inputs.add(sc.nextLine());
		}
		inputs.add("Epsilon");

		RE_To_E_NFA reToENFA = new RE_To_E_NFA(inputs);
		ArrayList<State_DS> curr_E_NFATable = new ArrayList<State_DS>();
		reToENFA.reToE_NFA(0, curr_E_NFATable, reElements);
		curr_E_NFATable = reToENFA.e_NFATable;
		NFAToDFAEpsilon ntde = new NFAToDFAEpsilon();
		ArrayList<String> nfaStates = new ArrayList<String>();
		ArrayList<String[]> nfaTable = new ArrayList<String[]>();
		for (State_DS state : curr_E_NFATable) {
			nfaStates.add(state.stateName);
			String[] nfaRow = new String[inputs.size()];
			int index = 0;
			for (ArrayList<String> transition : state.transition) {
				String row = transition.toString();
				nfaRow[index] = row.substring(1, row.length() - 1).trim();
				index++;
			}
			nfaTable.add(nfaRow);
		}

		System.out.println("NFA :-");
		ntde.display(nfaTable, nfaStates, inputs);
		ntde.thompsonSubsetConstruction(nfaTable, nfaStates, inputs);
	}
}
