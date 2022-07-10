import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) {
	Socket s;	
	PrintWriter pw;
	BufferedReader br,br1;
	
		try
		{
			s=new Socket(InetAddress.getLocalHost().getHostName(), 2000);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			br1=new BufferedReader(new InputStreamReader(System.in));
			while(true)
			{
			System.out.println("Enter the Number1 :");
			String a=br1.readLine();
			System.out.println("Enter the Number2 :");
			String b=br1.readLine();
			System.out.println("Enter the Number3 :");
			String c=br1.readLine();
			pw.println(a);
			pw.println(b);
			pw.println(c);
			pw.flush();
			System.out.println(br.readLine());
			System.out.println("Stop?");
			if(br1.readLine().equalsIgnoreCase("yes"))
			{
				break;
			}
			}
			}
		catch(Exception e){
			
		}
	}

}
