package entropyGain;

import java.util.ArrayList;

public class EntroptGain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Methods m = new Methods();
		ArrayList<ArrayList<String>> datas = m.readFromCsv("E:\\ID3.csv");
		String[] columnNames=datas.get(0).get(0).split(",");
		String[][] data=new String[datas.get(1).size()][columnNames.length];
		String[][] tuple=new String[datas.get(2).size()][columnNames.length];
		for (int i = 0; i < data.length; i++) {
			data[i]=datas.get(1).get(i).split(",");
		}
		for (int i = 0; i < tuple.length; i++) {
			tuple[i]=datas.get(2).get(i).split(",");
		}
		m.arrangement(data, columnNames);;		
	}

	
}
