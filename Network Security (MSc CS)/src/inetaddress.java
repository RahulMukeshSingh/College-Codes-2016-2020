import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class inetaddress {
	
	
	public static void main(String[] args) {
	double max=99.99;
	double min=22.22;
	double height=Math.round((max-min)*10D)/100D;
	System.out.println(height);
	//System.out.println(height);
	String[] arr=new String[11];
	double last=min;
	arr[0]=0+"-"+Math.round(last*100D)/100D;
	for (int i = 1; i < arr.length; i++) {
		arr[i]=Math.round(last*100D)/100D+"-"+Math.round((last+height)*100D)/100D;
		last=last+height;
		}
	for (String string : arr) {
		System.out.println(string);
	}
	String xx="45             59";
	String[] x=new String[]{"0","1","2","5","4"};
	String hh="A,B,C,D,E,F";
	System.out.println(hh.substring(2,hh.length()-2));
	System.out.println();
	String g="=100";
	System.out.println(g.contains(">="));
	}

}
