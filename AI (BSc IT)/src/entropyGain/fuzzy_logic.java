package entropyGain;
import java.util.Scanner;
 public class fuzzy_logic {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String y;
		do{
		System.out.println("Enter number of candidates");
		int n=sc.nextInt();
		System.out.println("enter membership value for graduation");
		double grad[]=new double[n];
		double inter[]=new double[n];
		for(int i=0;i<n;)
		{
			grad[i]=sc.nextDouble();
			if(grad[i]>1.0 || grad[i]<0.0)
				System.out.println("Enter value in range 0.0 - 1.0. Enter again!");
			else
				i++;			
		}
		System.out.println("Enter membership values for class 12th");
		for(int i=0;i<n;i++)
		{
			inter[i]=sc.nextDouble();
			if(inter[i]>1.0 || inter[i]<0.0)
				{
				System.out.println("Enter value in range 0.0 - 1.0");
				i--;
				}			
		}		
		System.out.println("enter target value");
		double target=sc.nextDouble();
	    int ch;
		System.out.println("enter choice: 1-max | 2-min | 3-complimentary");
		ch=sc.nextInt();
		switch(ch)
		{
		case 1: maximum(grad,inter,target); break;
		case 2: minimum(grad,inter,target); break;
		case 3: System.out.println("enter array whose complimentary is required 4-grad | 5-inter");
		        int s=sc.nextInt();
		        if(s==4)
		        	complimentary(grad, target);
		        if(s==5)
		        	complimentary(inter, target);
		        break;
		        default:
		        	System.out.println("wrong choice");
		}
		System.out.println();
		System.out.println("Do you want to continue? Press y/n");
		 y=sc.next();
		}while(y.equalsIgnoreCase("y"));
		        }
		
	private static void complimentary(double[] grad, double tar) {
		double comp[]=new double[grad.length];
		for(int i=0;i<grad.length;i++)
		{
			comp[i]=1.0-grad[i];
		}
		System.out.println("Qualifying candidates:");
		for(int i=0;i<grad.length;i++)
		{
			if(comp[i]>=tar)
				System.out.print(i+1+" ");
		}
	}
	private static void minimum(double[] grad, double[] inter,double tar) {
		double min[]=new double[grad.length];
		for(int i=0;i<grad.length;i++)
		{
			if(grad[i]<=inter[i])
			min[i]=grad[i];
			else
				min[i]=inter[i];
		}
		System.out.println("Qualifying candidates:");
		for(int i=0;i<grad.length;i++)
		{
			if(min[i]>=tar)
				System.out.print(i+1+" ");
		}
	}

	private static void maximum(double[] grad, double[] inter, double tar) {
		double max[]=new double[grad.length];
		for(int i=0;i<grad.length;i++)
		{
			if(grad[i]>=inter[i])
			max[i]=grad[i];
			else
				max[i]=inter[i];
		}
		System.out.println("Qualifying candidates:");
		for(int i=0;i<grad.length;i++)
		{
			if(max[i]>=tar)
				System.out.print(i+1+" ");
		}
}}
