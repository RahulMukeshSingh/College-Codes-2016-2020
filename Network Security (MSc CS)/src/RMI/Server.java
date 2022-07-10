package RMI;

import java.rmi.Naming;

public class Server
{
	public static void main(String args[])
	{
try{
calculator c=new cal();
Naming.rebind("rmi://localhost/cal",c);}
catch(Exception e)
{System.out.println(e);}}
}