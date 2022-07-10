package FeedForward;

import java.util.Iterator;
import java.util.Random;

public class neuralNetworkMethods {
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
	public double[] getEderivatives(double[] input,double[] output, double[] target, double[] weights)
	{
		double[] getAllE=new double[output.length];
		for(int j=0;j<input.length;j++)
		{
		//int i=0;
		getAllE[j]=0;
		for (int i=0; i < getAllE.length; i++) {
			getAllE[j]+=(output[i]-target[i])*(output[i]*(1-output[i]))* getRespectiveWeights(input, output, i,j,weights);
			
		
		}
		}
		return getAllE;
	}
	public double getRespectiveWeights(double[] input, double[] output,int indexOfOuput,int indexOfInput,double[] weights)
	{
		double weight = 0;
		int weightIndexIter = 0;
		int out = 0, in = 0;
		boolean flag = false;
		
		for (out = 0; out < output.length; out++) {
			for (in = 0; in < input.length; in++) {
				if (in==indexOfInput && out==indexOfOuput) {
					flag = true;
					weight=weights[weightIndexIter];
					break;
				}
				weightIndexIter++;
			}
			if (flag) {
				break;
			}
		}
		
		
		return weight;
		
	}
	public boolean getOutputSame(double[] input,double[] target)
	{
	
	boolean result=true;
	for (int i = 0; i < input.length; i++) {
		if(Math.abs(input[i]-target[i])<0.001)
		{
			result=result&& true;
		}
		else
		{
			result=result&& false;
		}
	}	
		
		return result;
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

	public double[] getCorrectedWeights(double[] input, double[] output, double[] target, double[] weights) {
		double[] correctedWeights = new double[input.length * output.length];
		for (int i = 0; i < correctedWeights.length; i++) {
			int[] indexOfInputAndOutput = getInputAndOutputIndex(input, output, i);
			double out = output[indexOfInputAndOutput[1]];
			double tar = target[indexOfInputAndOutput[1]];
			double outofh = input[indexOfInputAndOutput[0]];
			double adjustment = (out - tar) * (out * (1 - out)) * outofh;
			correctedWeights[i] = weights[i] - (0.5 * adjustment);
		}
		return correctedWeights;
	}
	public double[] getNextCorrectedWeights(double[] E,double[] input, double[] output, double[] weights) {
		double[] correctedWeights = new double[input.length * output.length];
		int j=0;
		for (int i = 0; i < correctedWeights.length; ) {
			int[] indexOfInputAndOutput = getInputAndOutputIndex(input, output, i);
			double out = output[indexOfInputAndOutput[1]];
			
			double outofh = input[indexOfInputAndOutput[0]];
			double adjustment = E[j] * (out * (1 - out)) * outofh;
			correctedWeights[i] = weights[i] - (0.5 * adjustment);
			i++;
			if(i%input.length==0)
			{
				j++;
			}
			
		}
		return correctedWeights;
	}
	public int[] getInputAndOutputIndex(double[] input, double[] output, int weightIndex) {
		int[] inputAndoutputIndex = new int[2];
		int weightIndexIter = 0;
		int out = 0, in = 0;
		boolean flag = false;
		for (out = 0; out < output.length; out++) {
			for (in = 0; in < input.length; in++) {
				if (weightIndexIter == weightIndex) {
					flag = true;
					break;
				}
				weightIndexIter++;
			}
			if (flag) {
				break;
			}
		}
		inputAndoutputIndex[0] = in;
		inputAndoutputIndex[1] = out;
		
		return inputAndoutputIndex;
	}

	public double[] getRandomWeights(double[] input) {
		double[] weights = new double[input.length * input.length];
		Random r = new Random();
		for (int i = 0; i < weights.length; i++) {
			weights[i] = r.nextInt(2) - 1;
		}
		return weights;
	}
	
}
