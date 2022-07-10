package naiveBayes;

import java.io.BufferedReader;
import java.io.FileReader;

public class csvread {

	public String csvreadnaiveBayes(String filelocation) {
		BufferedReader br = null;
		String line = "", value = "", output = "", tuple = "", columnName = "", columnIndex = "";
		try {

			br = new BufferedReader(new FileReader(filelocation));
			int i = 0, idindex = 0;
			String[] cellvalue = null;
			while ((line = br.readLine()) != null) {
				cellvalue = line.split(",");

				if (i == 0) {
					int j = 0;

					for (String idcheck : cellvalue) {
						if (idcheck.trim().equalsIgnoreCase("id")) {
							idindex = j;

						} else if (j < cellvalue.length - 1) {
							columnName += idcheck + " ";
							columnIndex += j + " ";
						}
						j++;
					}
				} else {
					if (cellvalue[cellvalue.length - 1].equals("?")) {
						tuple += line + " ";
					} else {
						value += line + " ";
					}
				}
				i++;
			}
			br.close();
			output = columnName.trim().replace(" ", ",").trim() + "@" + value.trim() + "@" + String.valueOf(idindex)
					+ "@" + tuple.trim() + "@" + columnIndex.trim().replace(" ", ",").trim();
		} catch (Exception e) {

		}

		return output.trim();
	}

}
