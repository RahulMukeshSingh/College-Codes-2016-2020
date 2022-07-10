package geneticsTransportation;

import java.util.Scanner;

public class geneticsTransportationAlgo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Numbers of Same Generation Values (Only Minimum) for Stoping:");
		int stopSameGeneration = sc.nextInt();
		geneticsTransportationMethods gtm = new geneticsTransportationMethods();
		String fileLocation="E:\\transportation.csv";
		gtm.arrangement(stopSameGeneration,fileLocation);

	}

}
