package Genetics;

import java.util.ArrayList;
import java.util.Random;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

class geneticsmethods {
	public ArrayList<geneticsinput> replacebyid(ArrayList<geneticsinput> gi, ArrayList<String> fitness,
			ArrayList<String> newpopulation) {
		geneticsmethods gm = new geneticsmethods();

		String[] getindexes = gm.getIdandIndex(gi, fitness).split(" ");
		String minfitnessid = getindexes[0];
		String replaceid = getindexes[1];
		int maxfitnessindex = Integer.valueOf(getindexes[2]);
		ArrayList<geneticsinput> newgi = new ArrayList<geneticsinput>();
		String cross = "";
		int i = 0;

		for (geneticsinput rows : gi) {

			if (rows.id.equals(minfitnessid) && rows.mate.equals(minfitnessid)) {
				cross = gm.getCrossOverPoints(newpopulation.get(maxfitnessindex), replaceid, replaceid, newgi);
				newgi.add(new geneticsinput(replaceid, newpopulation.get(maxfitnessindex), replaceid, cross,
						gm.getMutationPoints(newpopulation.get(maxfitnessindex))));

			} else if (rows.id.equals(minfitnessid)) {
				cross = gm.getCrossOverPoints(newpopulation.get(maxfitnessindex), replaceid, rows.mate, newgi);
				newgi.add(new geneticsinput(replaceid, newpopulation.get(maxfitnessindex), rows.mate, cross,
						gm.getMutationPoints(newpopulation.get(maxfitnessindex))));

			} else if (rows.mate.equals(minfitnessid)) {
				cross = gm.getCrossOverPoints(newpopulation.get(i), rows.id, replaceid, newgi);
				newgi.add(new geneticsinput(rows.id, newpopulation.get(i), replaceid, cross,
						gm.getMutationPoints(newpopulation.get(i))));

			} else {
				cross = gm.getCrossOverPoints(newpopulation.get(i), rows.id, rows.mate, newgi);
				newgi.add(new geneticsinput(rows.id, newpopulation.get(i), rows.mate, cross,
						gm.getMutationPoints(newpopulation.get(i))));
			}
			i++;
		}
		return newgi;
	}

	public String getIdandIndex(ArrayList<geneticsinput> gi, ArrayList<String> fitness) {

		double max = 0, min = Double.valueOf(fitness.get(0));
		int index = 0, minindex = 0, maxindex = 0;
		for (String value : fitness) {
			if (Double.valueOf(value) > max) {
				max = Double.valueOf(value);
				maxindex = index;
			}
			if (Double.valueOf(value) < min) {
				min = Double.valueOf(value);
				minindex = index;
			}
			index++;
		}

		return gi.get(minindex).id + " " + gi.get(maxindex).id + " " + String.valueOf(maxindex);
	}

	public String crossover(String A, String B, String crossover) {
		String[] crossoverr = crossover.split(",");

		int[] crossoverreal = new int[crossoverr.length + 1];
		int i = 0;
		for (String cr : crossoverr) {
			crossoverreal[i] = Integer.parseInt(cr);
			i++;
		}
		crossoverreal[i] = A.length();
		int j = 0;
		String[] A_part = new String[crossoverr.length + 1];
		String[] B_part = new String[crossoverr.length + 1];
		int part = 0;
		for (int cr : crossoverreal) {
			A_part[j] = A.substring(part, cr);
			B_part[j] = B.substring(part, cr);
			part = cr;
			j++;
		}
		String A_Child = "";

		for (int n = 0; n < A_part.length; n++) {
			if (n % 2 == 0) {
				A_Child += A_part[n];

			} else {
				A_Child += B_part[n];

			}
		}
		return A_Child;
	}

	public String validate(String A, String B, int maxlength) {
		String add1 = "", add2 = "";
		int max = 4;
		if (maxlength > max) {
			max = maxlength;
		}
		if (A.length() < max) {
			int i = 0;
			while (i < (max - A.length())) {
				add1 += "0";
				i++;
			}
		}
		if (B.length() < max) {
			int i = 0;
			while (i < (max - B.length())) {
				add2 += "0";
				i++;
			}
		}

		return add1 + A + " " + add2 + B;
	}

	public String mutation(String newpopulation, String mutation) {
		String newpopulation1 = "";
		if (mutation != "0") {
			for (int i = 0; i < newpopulation.length(); i++) {
				if (i == (Integer.valueOf(mutation) - 1)) {
					if (newpopulation.charAt(i) == '1') {
						newpopulation1 += "0";

					} else {
						newpopulation1 += "1";
					}
				} else {
					newpopulation1 += newpopulation.charAt(i);

				}
			}

		} else {
			newpopulation1 = newpopulation;
		}
		return newpopulation1;
	}

	public String evaluation(String replace, String equation) throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		equation = equation.replace("x", replace);
		return engine.eval(equation).toString();
	}

	public String dectobinary(String decimal) {
		String binary = "";
		int input = Integer.valueOf(decimal);
		while (input > 0) {
			binary = binary + String.valueOf(input % 2);
			input = input / 2;
		}
		StringBuffer out = new StringBuffer(binary);

		return out.reverse().toString();
	}

	public double getInitialGenerationValue(ArrayList<geneticsinput> gi, String equation)
			throws NumberFormatException, ScriptException {
		geneticsmethods gm = new geneticsmethods();

		double sum = 0;

		for (geneticsinput rows : gi) {
			sum += Double.valueOf(gm.evaluation(String.valueOf(Integer.parseInt(rows.selection, 2)), equation));

		}
		sum = sum / gi.size();
		return Math.round(sum * 100D) / 100D;
	}

	public double getGenerationValue(ArrayList<String> fitness) {
		double sum = 0;
		for (String rows : fitness) {
			sum += Double.valueOf(rows);
		}
		sum = sum / fitness.size();
		return Math.round(sum * 100D) / 100D;
	}

	public String getMate(ArrayList<geneticsinput> gi, String searchId, String mateId) {
		String mate = "";
		for (geneticsinput rows : gi) {
			if (rows.id.equals(mateId) && rows.mate.equals(searchId)) {
				mate = rows.selection;
			}
		}
		return mate;
	}

	public void inputarrangement(ArrayList<geneticsinput> gi, String equation)
			throws NumberFormatException, ScriptException {
		geneticsmethods gm = new geneticsmethods();

		double prePastGeneration = gm.getInitialGenerationValue(gi, equation), pastGeneration = 0,
				presentGeneration = 0;
		String[] validatee = null;
		String crossoverr = "", mutationn = "", fitnes = "";
		ArrayList<String> fitness = new ArrayList<String>();
		ArrayList<String> newpopulation = new ArrayList<String>();
		System.out.println("Id" + "\t" + "Initial Selection" + "\t" + "Initial Binary Selection" + "\t" + "Mate" + "\t"
				+ "Fitness");
		System.out.println();
		System.out.println(
				"----------------------------------------------------------------------------------------------------------");
		for (geneticsinput rows : gi) {
			
			validatee = gm.validate(rows.selection, gm.getMate(gi, rows.id, rows.mate), gm.getHighestLength(gi))
						.split(" ");
			
			fitnes = gm.evaluation(String.valueOf(Integer.parseInt(rows.selection, 2)), equation);
			newpopulation.add(validatee[0]);
			fitness.add(fitnes);

			System.out.println(rows.id + "\t" + Integer.parseInt(validatee[0], 2) + " \t   " + validatee[0] + "\t"
					+ rows.mate + "\t" + fitnes);
		}
		gi = new ArrayList<geneticsinput>(gm.replacebyid(gi, fitness, newpopulation));
		System.out.println("Initial Generation Value : " + prePastGeneration);
		System.out.println();
		double maxAnswer=0;
		String maxSelectionAnswer="";
		for (int i = 0; i < fitness.size(); i++) {
			if(Double.valueOf(fitness.get(i))>maxAnswer)
			{
			maxAnswer=Double.valueOf(fitness.get(i));
			maxSelectionAnswer=newpopulation.get(i);
			}
		}

		int iter = 0;
		do {
			if (iter > 1) {
				prePastGeneration = pastGeneration;
			}
			if (iter > 0) {
				pastGeneration = presentGeneration;
			}
			System.out.println("Id" + "\t" + "Decimal Selection" + "\t" + "Binary Selection" + "\t" + "Mate" + "\t"
					+ "Crossover" + "\t" + "Mutation" + "\t" + "New Population" + "\t" + "Decimal New Population" + "\t"
					+ "Fitness");
			System.out.println();
			System.out.println(
					"----------------------------------------------------------------------------------------------------------");
			fitness.removeAll(fitness);
			newpopulation.removeAll(newpopulation);
			System.out.println();
			for (geneticsinput rows : gi) {
				if (iter == 0) {
					validatee = gm.validate(rows.selection, gm.getMate(gi, rows.id, rows.mate), gm.getHighestLength(gi))
							.split(" ");
				} else {
					validatee = new String[] { rows.selection, gm.getMate(gi, rows.id, rows.mate) };
				}
				crossoverr = gm.crossover(validatee[0], validatee[1], rows.crossover);
				mutationn = gm.mutation(crossoverr, rows.mutation);
				fitnes = gm.evaluation(String.valueOf(Integer.parseInt(mutationn, 2)), equation);
				newpopulation.add(mutationn);
				fitness.add(fitnes);

				System.out.println(rows.id + "\t" + Integer.parseInt(validatee[0], 2) + "\t" + validatee[0] + "\t"
						+ rows.mate + "\t" + rows.crossover + "\t" + rows.mutation + "\t" + mutationn + "\t"
						+ Integer.parseInt(mutationn, 2) + "\t" + fitnes);
			}
			gi = new ArrayList<geneticsinput>(gm.replacebyid(gi, fitness, newpopulation));
			presentGeneration = gm.getGenerationValue(fitness);
			System.out.println();
			System.out.println("Generation Value : " + presentGeneration);
			System.out.println();
			for (int i = 0; i < fitness.size(); i++) {
				if(Double.valueOf(fitness.get(i))>maxAnswer)
				{
				maxAnswer=Double.valueOf(fitness.get(i));
				maxSelectionAnswer=newpopulation.get(i);
				}
			}
			iter++;
		} while (Math.abs(((pastGeneration) - (presentGeneration))) >= 0.1
				|| Math.abs(((pastGeneration) - (prePastGeneration))) >= 0.1);
		System.out.println("Total Rounds : " + (iter+1));
		System.out.println("Best Selection : " + maxSelectionAnswer);
		System.out.println("Best Fitness : " + maxAnswer);
		
	}

	public int getHighestLength(ArrayList<geneticsinput> gi) {
		int maxlength = 0;
		for (geneticsinput input : gi) {
			if (input.selection.length() > maxlength) {
				maxlength = input.selection.length();
			}
		}
		return maxlength;
	}

	public String getCrossOverPoints(String selection, String id, String mate, ArrayList<geneticsinput> gi) {
		Random r = new Random();

		String crossoverpoint = "";
		for (geneticsinput rows : gi) {
			if (rows.id.equals(mate) && rows.mate.equals(id)) {
				crossoverpoint = rows.crossover;
			}
		}
		if (crossoverpoint.isEmpty()) {
			int len = selection.length();
			int[] possibility = new int[] { 0, 1, 1, 0 };
			int index = r.nextInt(4);
			int temp = 0;
			if (possibility[index] == 0) {
				crossoverpoint = String.valueOf(r.nextInt(len - 1) + 1);
			} else {
				temp = len / 2;
				crossoverpoint = String.valueOf(r.nextInt(temp - 1) + 1) + ","
						+ String.valueOf(r.nextInt(len - temp - 1) + temp + 1);
			}
		}
		return crossoverpoint;
	}

	public String getMutationPoints(String selection) {
		Random r = new Random();

		String mutationpoint = "";
		int[] possibility = new int[] { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 };
		int index = r.nextInt(10);
		if (possibility[index] == 0) {
			mutationpoint = "0";
		} else {
			mutationpoint = String.valueOf(r.nextInt(selection.length() + 1));
		}
		return mutationpoint;
	}

	public String initialValidate(ArrayList<geneticsinput> gi, String A) {
		String A_New = "";
		int maxlen = getHighestLength(gi);
		int len = A.length();
		int[] findmax = new int[] { maxlen, len, 4 };
		int maxx = 0;
		for (int i : findmax) {
			if (i > maxx) {
				maxx = i;
			}
		}
		if (maxx > len) {
			for (int i = 0; i < (maxx - len); i++) {
				A_New += "0";
			}
		}
		return A_New + A;
	}
}
