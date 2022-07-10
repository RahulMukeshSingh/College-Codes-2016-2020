import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
ServerSocket ss;
Socket s;
BufferedReader br;
PrintWriter pw;
int num;
int  fact;
try {
	
	ss = new ServerSocket(1318);
	s=ss.accept();
	br=new BufferedReader(new InputStreamReader(s.getInputStream()));
	pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
	while(true)
	{
	num=Integer.valueOf(br.readLine());
    fact=1;
	while(num>0)
    {
    	fact*=num;
    	num--;
    }
	pw.println(fact);
	pw.flush();
    }
	}
catch (IOException e) {e.printStackTrace();}}}
