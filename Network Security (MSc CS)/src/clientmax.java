import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class clientmax {


	public static void main(String[] args) {
		Socket s;
		PrintWriter pw;
		BufferedReader br,br1;
		InetAddress ia;
		try
		{
		s=new Socket(InetAddress.getLocalHost().getHostName(), 1710);
		
		pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()) );
		br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		br1=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number:");
		String in=br1.readLine();
		pw.println(in);
		pw.flush();
	    System.out.println("Max is "+Integer.valueOf(br.readLine()));
	   
		}
        catch(IOException io)
		{
	io.printStackTrace();
		}
	}

}
