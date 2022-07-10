import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udpclientreverse {
	public static void main(String[] args) {
		DatagramSocket dss;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		byte[] b1, b2;
		String in;
		DatagramPacket dpp, dpp1;
		try {
			dss = new DatagramSocket();

			while (true) {
				System.out.println("Enter the number : ");
				b1 = new byte[1024];
				in = br.readLine();
				b2 = new byte[in.length()];

				b1 = in.getBytes();
				dpp = new DatagramPacket(b1, b1.length, InetAddress.getByName("localhost"), 2100);
				dss.send(dpp);
				dpp1 = new DatagramPacket(b2, b2.length);
				dss.receive(dpp1);
				System.out.println("Reverse is " + new String(dpp1.getData()));
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
