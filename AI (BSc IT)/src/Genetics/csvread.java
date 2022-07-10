package Genetics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class csvread {

	public ArrayList<String> csvreadGenetics(String filelocation) {
		BufferedReader br = null;
		String line = "";
		int selectionIndex = -1, idIndex = -1, mateIndex = -1;
		ArrayList<String> data = new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(filelocation));
			int i = 0;
			while ((line = br.readLine()) != null) {

				if (i == 0) {
					String[] part = line.split(",");
					for (int j = 0; j < part.length; j++) {
						if (part[j].equalsIgnoreCase("id")) {
							idIndex = j;
						} else if (part[j].equalsIgnoreCase("selection")) {
							selectionIndex = j;
						} else if (part[j].equalsIgnoreCase("mate")) {
							mateIndex = j;
						}
					}
					data.add(idIndex + "," + selectionIndex + "," + mateIndex);
				} else {
					data.add(line);
				}
				i++;
			}
			br.close();
		} catch (Exception e) {

		}
		return data;
	}

}
