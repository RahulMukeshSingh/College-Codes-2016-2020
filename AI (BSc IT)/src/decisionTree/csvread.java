package decisionTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csvread {

	public ArrayList<ArrayList<String>> csvreadDecisionTree(String filelocation) {
		BufferedReader br = null;
		String lines = "";
		ArrayList<ArrayList<String>> datas=new ArrayList<ArrayList<String>>();
		ArrayList<String> output = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(filelocation));
			
			while ((lines = br.readLine()) != null) {
				String[] line=lines.split(",");
				lines="";
				for (int i = 0; i < line.length; i++) {
					if(i==0)
					{
						id.add(line[i]);
					}
					else
					{
						lines+=line[i]+" ";
					}
				}
				
				output.add(lines.trim().replace(" ", ","));
			}
			
			datas.add(id);
			datas.add(output);
			br.close();
		} catch (Exception e) {

		}
		return datas;
	}

}
