import java.rmi.Naming;

public class RMIserver {

	public static void main(String[] args) {
		try
		{
			RMIInterface inter=new RMIMethods();
			Naming.rebind("rmi://localhost//RMIMethods", inter);
			
		}
		catch(Exception e)
		{
			
		}

	}

}
