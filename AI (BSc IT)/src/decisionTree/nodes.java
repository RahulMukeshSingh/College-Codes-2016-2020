package decisionTree;

public class nodes {

	int id;
	String values;
	String associatedParentValue;
	String remainingRowIndex;

	public nodes(int id, String values, String associatedParentValue, String remainingRowIndex) {
		this.id = id;
		this.values = values;
		this.associatedParentValue = associatedParentValue;
		this.remainingRowIndex = remainingRowIndex;
	}
}
