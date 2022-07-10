import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udpclientfibbo {
	public static void main(String[] args) {
		DatagramSocket dss;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		byte[] b1, b2, b3;
		String in, in1;
		DatagramPacket dpp, dpp1, dpp2;
		try {
			dss = new DatagramSocket();
			while (true) {
				System.out.println("Enter the first number : ");
				b1 = new byte[1024];
				in = br.readLine();
				System.out.println("Enter the second number : ");
				b3 = new byte[1024];
				in1 = br.readLine();
				b2 = new byte[1024];
				b1 = in.getBytes();
				dpp = new DatagramPacket(b1, b1.length, InetAddress.getByName("localhost"), 2110);
				dss.send(dpp);
				b3 = in1.getBytes();
				dpp2 = new DatagramPacket(b3, b3.length, InetAddress.getByName("localhost"), 2110);
				dss.send(dpp2);
				dpp1 = new DatagramPacket(b2, b2.length);
				dss.receive(dpp1);
				System.out.println("Output is " + new String(dpp1.getData(), 0, dpp1.getLength()));
				System.out.println("If you want to continue then just type and if not type no!!!!!");
				if (br.readLine().equalsIgnoreCase("no")) {
					break;
				}
			}
			System.out.println("Ended");
			dss.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
