package FeedForward;

import java.util.Iterator;
import java.util.Scanner;

public class neuralNetwork {

	public static void main(String[] args) {
		double[] input = { 0.05, 0.10 };
		double[] input1 = input;
		neuralNetworkMethods nm = new neuralNetworkMethods();
		// double[] weights=nm.getRandomWeights(input);
		double[] weights = { 0.15, 0.20, 0.25, 0.30 };
		int q = 0;
		double[] correctedWeights = null, correctedWeights1 = null;
		while (true) {
			
			System.out.println("-------------------------    Iteration No : "+(q+1)+"    --------------------------");
			if (q != 0) {
				weights = correctedWeights1;
			}
			double[] pastweights = weights;
			double[] bias = { 0.35, 0.60 };
			double[] HH = nm.netH(input, weights, bias, 0);
			int j = 0;
			double[] pastInput;
			for (double h : HH) {
				System.out.println("NET H" + j + " : " + h);
				j++;
			}
			input = nm.getOutH(HH);
			j = 0;
			for (double i : input) {
				System.out.println("OUT H" + j + " : " + i);
				j++;
			}

			if (q != 0) {
				weights = correctedWeights;
			} else {
				weights = new double[] { 0.40, 0.45, 0.5, 0.55 };
			}
			HH = nm.netH(input, weights, bias, 1);
			j = 0;
			for (double h : HH) {
				System.out.println("NET O" + j + " : " + h);
				j++;
			}
			pastInput = input;
			input = nm.getOutH(HH);
			j = 0;
			for (double i : input) {
				System.out.println("OUT O" + j + " : " + i);
				j++;
			}

			double[] target = { 0.01, 0.99 };

			if (nm.getOutputSame(input, target) || q==3) {
				System.out.println("Total Iteration : " + (q + 1));
				break;
			}
			System.out.println("TOTAL ERROR : " + nm.getErrorTotal(target, input));

			correctedWeights = nm.getCorrectedWeights(pastInput, input, target, weights);

			int i = 5;
			for (double cw : correctedWeights) {
				System.out.println("Updated w" + i + "=" + cw);
				i++;
			}

			double[] getE = nm.getEderivatives(pastInput, input, target, weights);
			correctedWeights1 = nm.getNextCorrectedWeights(getE, input1, pastInput, pastweights);
			i = 0;
			for (double cw : correctedWeights1) {
				System.out.println("Updated w" + i + "=" + cw);
				i++;
			}
			input = input1;

			q++;
		}

	}
}