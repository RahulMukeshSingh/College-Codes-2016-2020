import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpserverreverse {
	public static void main(String[] args) {
		DatagramSocket ds;

		byte[] b2, b;
		String in, out;
		DatagramPacket dp, dp2;

		try {
			ds = new DatagramSocket(2100);
			for (;;) {

				b = new byte[1024];
				dp = new DatagramPacket(b, b.length);
				ds.receive(dp);
				in = new String(dp.getData(), 0, dp.getLength());
				out = "";

				for (int i = in.length() - 1; i >= 0; i--) {
					out += in.charAt(i);
				}
				b2 = new byte[1024];
				b2 = out.getBytes();
				dp2 = new DatagramPacket(b2, b2.length, dp.getAddress(), dp.getPort());
				ds.send(dp2);
			}
		} catch (Exception e) {

		}

	}
}
