import java.math.BigInteger;
import java.util.Scanner;

public class rsa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the p and we will allocate closeby prime number : ");
		BigInteger p = new BigInteger(String.valueOf(Integer.parseInt(sc.nextLine())-1)).nextProbablePrime();
		System.out.println("Enter the q and we will allocate closeby prime number : ");
		BigInteger q = new BigInteger(String.valueOf(Integer.parseInt(sc.nextLine())-1)).nextProbablePrime();
		System.out.println("Plain Text : ");
		BigInteger plain=new BigInteger(sc.nextLine());
		BigInteger n = p.multiply(q);
		BigInteger tn = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		BigInteger e = new BigInteger("0"), d = new BigInteger("0");
		for (int i = 2; i < tn.intValue(); i++) {
			BigInteger u = tn.gcd(new BigInteger(String.valueOf(i)));
			if (u.equals(BigInteger.ONE)) {
				e = new BigInteger(String.valueOf(i));
				break;
			}
		}
		for (int i = 1; i < tn.intValue(); i++) {

			if (((new BigInteger(String.valueOf(i))).multiply(e)).mod(tn).equals(BigInteger.ONE)) {
				d = new BigInteger(String.valueOf(i));
				break;
			}
		}
BigInteger encrypt=	(plain.pow(e.intValue())).mod(n);
System.out.println("p is "+p);
System.out.println("q is "+q);
System.out.println("Encryption key is "+e);
System.out.println("Decryption key is "+d);
System.out.println("Encryption is " + (plain.pow(e.intValue())).mod(n));		
System.out.println("Decryption is " + (encrypt.pow(d.intValue())).mod(n));
	}

}
