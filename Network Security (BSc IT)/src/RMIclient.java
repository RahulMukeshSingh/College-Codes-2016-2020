import java.rmi.Naming;
import java.util.Scanner;

public class RMIclient {
	public static void main(String[] args) {
		try {
			Scanner sc=new Scanner(System.in);
			RMIInterface inter = (RMIInterface) Naming.lookup("rmi://localhost//RMIMethods");
			while(true)
			{
			System.out.println("Enter the number 1 : ");
			int x=sc.nextInt();
			System.out.println("Enter the number 2 : ");
			int y=sc.nextInt();
			System.out.println(inter.add(x, y)+" "+inter.sub(x, y));
			System.out.println("Exit?");
			if(sc.nextLine().equalsIgnoreCase("yes"))
			{
				break;
			}
			}
			} catch (Exception e) {

		}
	}
}
