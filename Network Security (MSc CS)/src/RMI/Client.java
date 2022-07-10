package RMI;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		try {
			calculator c = (calculator) Naming.lookup("rmi://localhost/cal");
			System.out.println("Enter 2 numbers");
			int x = sc.nextInt();
			int y = sc.nextInt();
			System.out.println("ans=" + c.add(x, y));
			System.out.println("ans=" + c.sub(x, y));
			System.out.println("ans=" + c.mul(x, y));
			System.out.println("ans=" + c.div(x, y));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}