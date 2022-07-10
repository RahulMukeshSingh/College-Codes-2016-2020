import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
final class client {

	

	public static void main(String[] args) {
		Socket c;
		PrintWriter pw;
		BufferedReader br,br1;
		String cont;
		Scanner sc=new Scanner(System.in);
		try
		{
			c = new Socket("localhost", 1318);
			
			pw=new PrintWriter(new OutputStreamWriter(c.getOutputStream()));
			
			br=new BufferedReader(new InputStreamReader(c.getInputStream()));
			br1=new BufferedReader(new InputStreamReader(System.in));
			cont="y";
			while(cont.equals("y"))
			{
            System.out.println("Enter the number:");
			String in=br1.readLine();
			pw.println(in);
			pw.flush();
		    System.out.println("Factorial is "+Integer.valueOf(br.readLine()));
		    System.out.println("Do you want to continue?");
		    cont=sc.next();
		}
			System.out.println("Ended");
			}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
