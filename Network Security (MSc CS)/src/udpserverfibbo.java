import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class udpserverfibbo {
	public static void main(String[] args) {
		DatagramSocket ds;
		byte[] b2, b, b1;
		String in, out, in1;
		DatagramPacket dp, dp1, dp2;
		int f1 = 0, f2 = 1, sum;
		try {
			ds = new DatagramSocket(2110);
			for (;;) {

				b = new byte[1024];
				dp = new DatagramPacket(b, b.length);
				b1 = new byte[1024];
				dp1 = new DatagramPacket(b1, b1.length);
				ds.receive(dp);
				in = new String(dp.getData(), 0, dp.getLength());
				ds.receive(dp1);
				in1 = new String(dp1.getData(), 0, dp1.getLength());
				out = "";
				sum = 0;
				f1 = 0;
				f2 = 1;
				ArrayList<String> al = new ArrayList<String>();
				al.add(String.valueOf(f1));
				al.add(String.valueOf(f2));
				while (true) {

					sum = f1 + f2;
					f1 = f2;
					f2 = sum;
					if (sum > Integer.valueOf(in1)) {
						break;
					}
					al.add(String.valueOf(sum));

				}
				for (String ss : al) {

					if (Integer.valueOf(ss) >= Integer.valueOf(in)) {
						out += ss + " ";
					}
				}
				
				b2 = new byte[1024];
				b2 = out.trim().getBytes();
				dp2 = new DatagramPacket(b2, b2.length, dp.getAddress(), dp.getPort());
				ds.send(dp2);
			}
		} catch (Exception e) {

		}

	}
}
