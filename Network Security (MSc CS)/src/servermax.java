import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class servermax {

	public static void main(String[] args) {
	ServerSocket ss;
	Socket s;
	BufferedReader br;
	PrintWriter pw;
	String[] num; 
	int max=0;
	try
	{
		ss=new ServerSocket(1710);
		s=ss.accept();
		br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		num=br.readLine().split(" ");
		for (String val : num) {
			if(Integer.valueOf(val)>max)
			{
max=Integer.valueOf(val);				
			}
		}
		pw.println(max);
		pw.flush();
	}
	catch(IOException ie)
	{
		ie.printStackTrace();
	}

	}

}
