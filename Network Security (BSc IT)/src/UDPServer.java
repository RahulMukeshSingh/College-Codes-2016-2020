import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

	public static void main(String[] args) {
		DatagramSocket ds;
		DatagramPacket rec,rec1,sen;
		byte[] recb,recb1,senb; 
		try {
			ds = new DatagramSocket(2100);
			while(true)
			{
			recb=new byte[1024];
			rec=new DatagramPacket(recb, recb.length);
			ds.receive(rec);
			recb1=new byte[1024];
			rec1=new DatagramPacket(recb1, recb1.length);
			ds.receive(rec1);
			String out=String.valueOf(Integer.parseInt(new String(rec.getData(), 0, rec.getLength()))+
					Integer.parseInt(new String(rec1.getData(), 0, rec1.getLength())));
			senb=out.trim().getBytes();
			sen=new DatagramPacket(senb, senb.length,rec.getAddress() ,rec.getPort());
			ds.send(sen);
			}
			
		} catch (Exception e) {

		}
	}

}
