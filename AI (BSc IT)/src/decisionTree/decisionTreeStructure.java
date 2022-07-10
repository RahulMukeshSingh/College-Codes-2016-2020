package decisionTree;

import java.util.ArrayList;

public class decisionTreeStructure {
	nodes parent;
	ArrayList<nodes> childs;

	public decisionTreeStructure(nodes parent, ArrayList<nodes> childs) {
		this.parent = parent;
		this.childs = childs;

	}
}
