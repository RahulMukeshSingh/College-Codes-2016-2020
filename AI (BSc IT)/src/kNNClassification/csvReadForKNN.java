package kNNClassification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class csvReadForKNN {
	public ArrayList<String> readFromCSVKNN(String fileLocation) {
		ArrayList<String> data = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileLocation));
			String row;
			String attr = new String(), idIndex = new String();
			String tup = new String(), val = new String();
			for (int i = 0; (row = br.readLine()) != null; i++) {
				if (i == 0) {
					String[] cells = row.split(",");
					for (int j = 0; j < cells.length; j++) {
						if (cells[j].equalsIgnoreCase("id")) {
							idIndex = String.valueOf(j);
						}
					}
					attr = row;
				} else {
					String[] cells = row.split(",");

					if (cells[cells.length - 1].equalsIgnoreCase("?")) {
						tup += row + " ";
					} else {
						val += row + " ";
					}

				}

			}
			data.add(idIndex.trim());
			data.add(attr.trim());
			data.add(val.trim());
			data.add(tup.trim());
		} catch (Exception e) {

		}

		return data;
	}
}
