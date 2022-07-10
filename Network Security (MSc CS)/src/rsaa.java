import java.math.BigInteger;
import java.util.Scanner;;
public class rsaa {
    public void rsaencrypt(int P,BigInteger p1,BigInteger q1)
    {
    BigInteger C=new BigInteger("0");
    
    int p=p1.intValue();
    int q=q1.intValue();
    int n=p*q;	
    int tn=(p-1)*(q-1);
    int e=0;
    int d=0;
  for(int i=2;i<tn;i++)
    {
  BigInteger u= (new BigInteger(String.valueOf(tn))).gcd(new BigInteger(String.valueOf(i)));
  BigInteger u1= new BigInteger("1");
  if(u.equals(u1))
    	{
	  		e=i;
    		break;
    	}
  }
for(d=1;d<tn;d++)
    {
    	if(((d*e)% tn)==1)
    	{
    		break;
    	}
    }
		BigInteger Pl;
        System.out.println("Plain text: "+P);
        System.out.println("p :"+p);
        System.out.println("q:"+q);
        System.out.println("n: "+n);
        System.out.println("tn: "+tn);
        System.out.println("Encryption key: "+e);
        System.out.println("Decryption key: "+d);

    	C=((new BigInteger(String.valueOf(P))).pow(e)).mod(new BigInteger(String.valueOf(n)));

    Pl=(C.pow(d)).mod(new BigInteger(String.valueOf(n)));
 
 
        System.out.println("Cipher text :"+C);
    System.out.println("Plain text :"+Pl);
    //  System.out.println(P+" "+p+" "+q+" "+tn+" "+C+" "+Pl);
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
rsaa r=new rsaa();

Scanner sc=new Scanner(System.in);
int P=0;
System.out.println("Enter the Plain Text:");
P=sc.nextInt();

System.out.println("Enter the p and we will allot you closeby prime number if it is not prime number:");
int p2=sc.nextInt()-1;
BigInteger p1=new BigInteger(String.valueOf(p2)).nextProbablePrime();
System.out.println("p as a prime number is "+p1);


System.out.println("Enter the q and we will allot you closeby prime number if it is not prime number:");
int q2=sc.nextInt()-1;
BigInteger q1=new BigInteger(String.valueOf(q2)).nextProbablePrime();
System.out.println("q as a prime number is "+q1);
r.rsaencrypt(P,p1,q1);
	}

}
