import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class csvreadKNN {

	public String csvread(String filelocation) {
		BufferedReader br = null;
		String line = "", value = "", output = "", tuple = "";
		try {

			br = new BufferedReader(new FileReader(filelocation));
			int i = 0, idindex = 0;
			String[] cellvalue = null;
			while ((line = br.readLine()) != null) {
				cellvalue = line.split(",");

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
						tuple += line + " ";
					} else {
						value += line + " ";
					}
				}
				i++;
			}
			br.close();
			output = value.trim() + "@" + String.valueOf(idindex) + "@" + tuple.trim();
			
		} catch (Exception e) {

		}

		return output.trim();
	}
}
	class KNN {
		private boolean isDouble(String in) {
			try {
				Double.parseDouble(in);
				return true;
			} catch (NumberFormatException n) {
				return false;
			} catch (Exception e) {
				return false;
			}

		}

		private ArrayList<ArrayList<String>> getNValues(String values) {

			ArrayList<ArrayList<String>> total = new ArrayList<ArrayList<String>>();
			ArrayList<String> parts = new ArrayList<String>();
			String[] tempspace = values.split(" ");// 4,5,6 7,8,9
			String[] tempcomma = null;
			int i = 0;// 4,5,6 7,8,9
			for (String valwithoutspace : tempspace) {
				tempcomma = valwithoutspace.split(",");// 4,5,6

				if (parts.isEmpty()) {
					for (String valwithoutcomma : tempcomma) {
						parts.add(valwithoutcomma); // 4

					}

				} else {
					i = 0;
					for (String valwithoutcomma : tempcomma) {
						parts.set(i, valwithoutcomma); // 4
						i++;
					}
				}
				total.add(new ArrayList<String>(parts));
			}
			return total;
		}

		private ArrayList<Double> getDistance(String values, String tuple, int colno, KNN k) {
			ArrayList<Double> dist = new ArrayList<Double>();
			ArrayList<ArrayList<String>> total = k.getNValues(values);
			String[] t = tuple.split(",");
			int i = 0;
			double distance = 0;
			boolean condition;
			for (ArrayList<String> parts : total) {
				i = 0;
				distance = 0;
				for (String val : parts) {
					if (colno != 0) {
						condition = (i != colno - 1);
					} else {
						condition = true;
					}
					if (k.isDouble(t[i]) && k.isDouble(val) && i != parts.size() - 1 && condition) {
						distance += Math.abs(Double.parseDouble(val) - Double.parseDouble(t[i]));
					}
					i++;
				}
				dist.add(distance);
			}
			return dist;
		}

		private ArrayList<String> getDistandOutput(String values, String tuple, int colno, KNN k) {
			ArrayList<Double> dist = k.getDistance(values, tuple, colno, k);
			ArrayList<String> distandoutput = new ArrayList<String>();
			ArrayList<ArrayList<String>> total = k.getNValues(values);
			int i = 0, j = 0;
			String output;
			for (ArrayList<String> part : total) {
				i = 0;
				output = "";
				for (String val : part) {
					if (i == part.size() - 1) {
						output = val;
					}
					i++;
				}
				distandoutput.add(String.valueOf(dist.get(j)) + " " + output);
				j++;
			}
			return distandoutput;
		}

		public String getKNNClassification(String values, String tuple, int colno) {
			String output = "";
			KNN k = new KNN();
			ArrayList<String> distandoutput = k.getDistandOutput(values, tuple, colno, k);
			String[] distandoutputclassify = null;
			int K = (int) Math.sqrt(distandoutput.size());
			String[] lastoutput = new String[K];
			double[] lastdist = new double[K];

			for (int j = 0; j < K; j++) {
				distandoutputclassify = distandoutput.get(j).split(" ");// 5.0 obes
				lastdist[j] = Double.parseDouble(distandoutputclassify[0]);
				lastoutput[j] = distandoutputclassify[1];
			}
			int i = 0, maxindex = 0, index = 0;
			double max = 0;
			for (String dao : distandoutput) {
				if (i >= K) {
					distandoutputclassify = dao.split(" ");
					double distiterate = Double.parseDouble(distandoutputclassify[0]);
					String outputiterate = distandoutputclassify[1];
					max = 0;
					maxindex = 0;
					index = 0;
					for (double ld : lastdist) {
						if (ld > max) {
							max = ld;
							maxindex = index;
						}
						index++;
					}
					if (distiterate < max) {
						lastdist[maxindex] = distiterate;
						lastoutput[maxindex] = outputiterate;
					}
				}
				i++;
			}
			int count = 0, maxcount = 0;
			for (String out : lastoutput) {
				count = 0;

				for (String out1 : lastoutput) {
					if (out.equals(out1)) {
						count++;
					}

				}
				if (count > maxcount) {
					maxcount = count;
					output = out;
				}
			}
			if (colno != 0) {
				String[] tup = tuple.split(",");
				output = tup[colno - 1] + " has " + output;
			}
			return output;
		}

	}



public class KNNExample {
	public static void main(String[] args) {
		KNN k=new KNN();
		
String[] val=(new csvreadKNN()).csvread("E:\\doctor.csv").split("@");
String values=val[0];
int colno=Integer.valueOf(val[1]);
String[] tuplee=val[2].split(" ");

for (String tuple : tuplee) {
	System.out.println(k.getKNNClassification(values, tuple,colno));
}
		

	}

}
