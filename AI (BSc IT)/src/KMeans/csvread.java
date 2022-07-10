package KMeans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class csvread {

	public String csvreadKmeans(String filelocation) {
		BufferedReader br = null;
		String line = "", output = "";
		int idindex = 0;
		try {

			br = new BufferedReader(new FileReader(filelocation));
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (i == 0) {
					String[] withoutComma = line.split(",");
					for (int j = 0; j < withoutComma.length; j++) {
						if (withoutComma[j].equalsIgnoreCase("id")) {
							idindex = j + 1;
						}
					}
				} else {
					output += line + " ";
				}
				i++;
			}
			br.close();
		} catch (Exception e) {

		}
		return  (String.valueOf(idindex)+"@"+output.trim()).trim();
	}

}
