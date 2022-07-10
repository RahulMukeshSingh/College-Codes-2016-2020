package kNN;
import java.io.BufferedReader;
import java.io.FileReader;
public class csvKNN {

	public String read(String file) {
		BufferedReader b = null;
		String li = "", val = "", out = "", tup = "";
		try {

			b = new BufferedReader(new FileReader(file));
			int i = 0, idindex = 0;
			String[] cellvalue = null;
			while ((li = b.readLine()) != null) {
				cellvalue = li.split(",");

				if (i == 0) {
					int j = 1;
					for (String idcheck : cellvalue) {
						if (idcheck.trim().equalsIgnoreCase("id")) {
							idindex = j;

						}
						j++;
					}
				} else {
					if (cellvalue[cellvalue.length - 1].equals("?")) {
						tup += li + " ";
					} else {
						val += li + " ";
					}
				}
				i++;
			}
			b.close();
			out = val.trim() + "@" + String.valueOf(idindex) + "@" + tup.trim();
			
		} catch (Exception e) {

		}

		return out.trim();
	}

}
