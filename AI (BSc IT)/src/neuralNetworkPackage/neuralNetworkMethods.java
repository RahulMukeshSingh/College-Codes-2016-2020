package neuralNetworkPackage;

import java.util.Random;

public class neuralNetworkMethods {
public double[] correctedWeights(double[] input,double[] weights,double classOfI)
{
double[] corrected=new double[input.length];
for (int i = 0; i < input.length; i++) {
corrected[i]=Math.round((weights[i]+(classOfI * input[i])) * 100D)/100D;	
}
return corrected;	
}

public double mulAndSum(double[] input,double[] weights)
{
double sum=weights[weights.length-1];
for (int i = 0; i < input.length-1; i++) {
sum+=weights[i]* input[i];
}
return sum;	
}

public double[] getRandomWeights(double[] input)
{
double[] weights=new double[input.length];
Random r=new Random();
for (int i = 0; i < weights.length; i++) {
	weights[i]=r.nextInt(20)-10;
}
return weights;
}
}
