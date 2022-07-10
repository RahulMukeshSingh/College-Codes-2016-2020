import java.io.BufferedReader;import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) {
ServerSocket ss;
Socket s;
BufferedReader br;
PrintWriter pw;
try
	{
	ss=new ServerSocket(2000);
	s=ss.accept();
	while(true)
	{
	br=new BufferedReader(new InputStreamReader(s.getInputStream()));
	pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
	String sum=String.valueOf(Integer.parseInt(br.readLine())+Integer.parseInt(br.readLine())+Integer.parseInt(br.readLine()));
	pw.println(sum);
	pw.flush();
	}
	}catch(Exception e)
	{
		
	}

	}

}
