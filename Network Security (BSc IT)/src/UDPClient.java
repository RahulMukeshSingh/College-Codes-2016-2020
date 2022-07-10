import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

	public static void main(String[] args) {
		DatagramSocket ds;
		DatagramPacket sen, sen1, rec;
		byte[] senb,senb1,recb;
		BufferedReader br;
		try {
			ds = new DatagramSocket();
			while(true)
			{
			br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the First Number : ");
			String a=br.readLine();
			System.out.println("Enter the Second number : ");
			String b=br.readLine();
			senb=new byte[1024];
			senb=a.getBytes();
			sen=new DatagramPacket(senb, senb.length, InetAddress.getByName("localhost"), 2100);
			senb1=new byte[1024];
			senb1=b.getBytes();
			sen1=new DatagramPacket(senb1, senb1.length, InetAddress.getByName("localhost"), 2100);
			ds.send(sen);
			ds.send(sen1);
			recb=new byte[1024];
			rec=new DatagramPacket(recb, recb.length);
			ds.receive(rec);
			System.out.println(new String(rec.getData(),0,rec.getLength()));
			System.out.println("stop?");
			if(br.readLine().equalsIgnoreCase("yes"))
			{
				break;
			}
			}} catch (Exception e) {

		}
	}

}
