package neuralNetworkPackage;

import java.util.Scanner;

public class neuralNetwork {

	public static void main(String[] args) {
		System.out.println("Enter the classification:");
		Scanner sc=new Scanner(System.in);
		double classOfI=sc.nextDouble();
		sc.nextLine();
		double[] input={-1,2,0,-4,1};
		neuralNetworkMethods nm=new neuralNetworkMethods();
		double[] weights=new double[input.length];
		double sum=0;
		while(true)
		{
		weights=nm.getRandomWeights(input);
		
		weights=nm.correctedWeights(input, weights, classOfI);
		
		sum=nm.mulAndSum(input, weights);
		if(classOfI==-1 && sum <0)
		{
			break;
		}
		if(classOfI==1 && sum >=0)
		{
			break;
		}
		}
		System.out.println("Answer:"+sum);
		System.out.println("Weights are got Classified So Adjusted Weights are :");
		for (int i = 0; i < weights.length; i++) {
			double d = weights[i];
			System.out.println(d);
		}
	}

}
