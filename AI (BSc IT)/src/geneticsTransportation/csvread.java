package geneticsTransportation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csvread {

	public ArrayList<transportationStructure> csvreadTransportation(String filelocation) {
		BufferedReader br = null;
		String line = "";
		ArrayList<transportationStructure> out = new ArrayList<transportationStructure>();
		double[][] values = null;
		ArrayList<String> places = new ArrayList<String>();
		try {
			int rows = 0, cols = 0;
			br = new BufferedReader(new FileReader(filelocation));
			while ((line = br.readLine()) != null) {
				if (rows == 0) {
					cols = line.split(",").length;
				}
				rows++;

			}
			br.close();
			br = new BufferedReader(new FileReader(filelocation));
			values = new double[rows - 1][cols - 1];
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] part = line.split(",");
				if (i == 0) {
					for (int j = 1; j < part.length; j++) {
						places.add(part[j]);
					}
				} else {
					for (int j = 1; j < part.length; j++) {
						values[i - 1][j - 1] = Double.valueOf(part[j]);
					}
				}
				i++;
			}
			br.close();

		} catch (Exception e) {

		}

		out.add(new transportationStructure(places, values));
		return out;
	}

}
