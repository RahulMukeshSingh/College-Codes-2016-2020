package Genetics;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.script.*;

public class geniticsAlgo {

	public static void main(String[] args) throws ScriptException {

		ArrayList<geneticsinput> rows = new ArrayList<geneticsinput>();
		Scanner sc = new Scanner(System.in);
		String id = "", selection = "", mate = "", crossover = "", mutation = "";
		System.out.print("Enter the fitness equation : ");
		String equation = sc.nextLine();
		geneticsmethods gm = new geneticsmethods();
		ArrayList<String> data = (new csvread()).csvreadGenetics("E:\\genetics.csv");
		int i = 0;
		int selectionIndex = 1, idIndex = 0, mateIndex = 2;
		String[] part = null;

		for (String line : data) {
			if (i == 0) {
				part = line.split(",");
				if (Integer.valueOf(part[0]) != -1) {
					idIndex = Integer.valueOf(part[0]);
				}
				if (Integer.valueOf(part[1]) != -1) {
					selectionIndex = Integer.valueOf(part[1]);
				}
				if (Integer.valueOf(part[2]) != -1) {
					mateIndex = Integer.valueOf(part[2]);
				}
			} else {
				part = line.split(",");
				id = part[idIndex];

				selection = part[selectionIndex];
				selection = gm.dectobinary(selection);
				mate = part[mateIndex];

				crossover = gm.getCrossOverPoints(selection, id, mate, rows);
				mutation = gm.getMutationPoints(selection);
				rows.add(new geneticsinput(id, gm.initialValidate(rows, selection), mate, crossover, mutation));
			}
			i++;
		}

		gm.inputarrangement(rows, equation);

	}
}
