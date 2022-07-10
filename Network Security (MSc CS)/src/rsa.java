import java.math.BigInteger;
import java.util.*;
public class rsa {
    public void rsaencrypt(int P)
    {
    BigInteger C=new BigInteger("0");
    
    
    BigInteger p1=new BigInteger(String.valueOf((int)(Math.sqrt(P)+5))).nextProbablePrime();
    BigInteger q1=new BigInteger(String.valueOf((int)(Math.sqrt(P))+17)).nextProbablePrime();
    int p=p1.intValue();
    int q=q1.intValue();
    int n=p*q;	
    int tn=(p-1)*(q-1);
    int e=0;
    int flag=0;
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
rsa r=new rsa();
Scanner sc=new Scanner(System.in);
int P=0;
System.out.println("Enter the Plain Text:");
P=sc.nextInt();
r.rsaencrypt(P);
	}

}
