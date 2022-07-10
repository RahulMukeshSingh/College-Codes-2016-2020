package FeedForward;

import java.util.Random;

public class neuralNetworkk {
	public double[] netH(double[] input, double[] weights, double[] bias, int iter) {
		double[] netHH = new double[input.length];
		int weightIndex = 0;
		for (int i = 0; i < netHH.length; i++) {
			for (int k = 0; k < input.length; k++) {
				netHH[i] += input[k] * weights[weightIndex];
				weightIndex++;
			}
			netHH[i] += bias[iter];
		}
		return netHH;
	}
	public double[] getOutH(double[] HH) {
		double[] outHH = new double[HH.length];
		for (int i = 0; i < outHH.length; i++) {
			outHH[i] = 1 / (1 + Math.pow(Math.E, -1 * HH[i]));
		}
		return outHH;
	}

	public double getErrorTotal(double[] target, double[] input) {
		double sumError = 0;
		for (int i = 0; i < input.length; i++) {
			sumError += (0.5) * Math.pow(target[i] - input[i], 2);
		}
		return sumError;
	}
	public double[] getRandomWeights(double[] input) {
		double[] weights = new double[input.length * input.length];
		Random r = new Random();
		for (int i = 0; i < weights.length; i++) {
			weights[i] = r.nextInt(2) - 1;
		}
		return weights;
	}
	public static void main(String[] args) {
		double[] input = { 0.05, 0.10 };
		neuralNetworkk nm = new neuralNetworkk();
		// double[] weights=nm.getRandomWeights(input);
		double[] weights = { 0.15, 0.20, 0.25, 0.30 };
			double[] bias = { 0.35, 0.60 };
			double[] HH = nm.netH(input, weights, bias, 0);
			int j = 0;
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
			// double[] weights=nm.getRandomWeights(input);
			weights = new double[] { 0.40, 0.45, 0.5, 0.55 };
			HH = nm.netH(input, weights, bias, 1);
			j = 0;
			for (double h : HH) {
				System.out.println("NET O" + j + " : " + h);
				j++;
			}
			input = nm.getOutH(HH);
			j = 0;
			for (double i : input) {
				System.out.println("OUT O" + j + " : " + i);
				j++;
			}

			double[] target = { 0.01, 0.99 };

			System.out.println("TOTAL ERROR : " + nm.getErrorTotal(target, input));


	}

}
