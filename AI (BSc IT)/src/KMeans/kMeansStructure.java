package KMeans;

import java.util.ArrayList;

public class kMeansStructure {
	public ArrayList<String> id;
	public ArrayList<String> val;
	public ArrayList<String> means;

	public kMeansStructure(ArrayList<String> id, ArrayList<String> val, ArrayList<String> means) {
		this.id = id;
		this.val = val;
		this.means = means;
	}
}
