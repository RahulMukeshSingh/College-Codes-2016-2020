import java.util.*;
class IDthree
{
public ArrayList<String> getValofNodes(String[] in)
{
	ArrayList<String> out=new ArrayList<String>();
	out.add(in[0]);
	int flag=0;
	for(int i=1;i<in.length;i++)
	{
		flag=0;
		for(int j=0;j<out.size();j++)
		{
			
			if(out.get(j).equals(in[i]))
			{
			flag++;	
			}
		}
		if(flag==0)
		{
			out.add(in[i]);
		}
	}
	return out;
}
public double IofS(String[] output)
{
IDthree idt=new IDthree();
ArrayList<String> s=idt.getValofNodes(output);
ArrayList<Double> ss=new ArrayList<Double>();
double flag;
for(int i=0;i<s.size();i++)
{
	flag=0;
	for(int j=0;j<output.length;j++)
	{
	if(s.get(i).equals(output[j]))
	{
	flag++;	
	}
	
}
	
	ss.add(flag);
}
double p=(ss.get(0))/(ss.get(0)+ss.get(1));
double n=(ss.get(1))/(ss.get(0)+ss.get(1));
double iofs=idt.iforanything(p, n);
return iofs;
}
public double iforanything(double p,double n)
{
	
	double iforr=0;
	double plog=0,nlog=0;
	    if(p>0)
	    {
	    	plog=Math.log(p)/Math.log(2);
	    }
	    if(n>0)
	    {
	    	nlog=Math.log(n)/Math.log(2);
	    }
	    
			iforr=-p * (plog) -n *(nlog);
	return iforr;
	
}

public double[] gain(String[][] input,String[] output)
{
	double p,n;
IDthree idt=new IDthree();	
double[] gains=new double[input[0].length];
String[] numattr=new String[input.length];
ArrayList<String> attrname;
ArrayList<String> out;
double flag;
double attrnodesval;
double[][] ynnodes;
double rem,summ;
for(int j=0;j<input[0].length;j++)
{
	
for(int i=0;i<input.length;i++)
{
numattr[i]=input[i][j];	
}

attrname=idt.getValofNodes(numattr);
out=idt.getValofNodes(output);
ynnodes=new double[attrname.size()][out.size()];

for(int u=0;u<attrname.size();u++)
{

for(int v=0;v<out.size();v++)
{
	flag=0;
	for(int w=0;w<output.length;w++)
	{
	if(out.get(v).equals(output[w]) && attrname.get(u).equals(numattr[w]))
	{
		flag++;
	}
	}
	ynnodes[u][v]=flag;
}
}
rem=0;
for(int u=0;u<attrname.size();u++)
{

summ=0;
for(int v=0;v<out.size();v++)
{

	summ+=	ynnodes[u][v];
		
}

p=ynnodes[u][0]/summ;

n=ynnodes[u][1]/summ;

rem+=(summ/output.length) * idt.iforanything(p, n);

}
gains[j]=idt.IofS(output)-rem;
}
return gains;
}

}
public  class DecisionTree {

	public static void main(String[] args) {
	
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter the number of attribute:");
	int attrlen=sc.nextInt();
	sc.nextLine();
	System.out.println("Enter the name of attribute:");
	String[] attr=new String[attrlen];
	for(int i=0;i<attrlen;i++)
	{
		attr[i]=sc.nextLine();
	}
	System.out.println("Enter the number of rows:");
	int row=sc.nextInt();
	sc.nextLine();
String[][] input=new String[row][attrlen];
for(int j=0;j<attrlen;j++)
{
	System.out.println("Enter the details for "+attr[j]);
for(int i=0;i<row;i++)
{
	
	input[i][j]=sc.nextLine();
	}
}
String[] output=new String[row];
System.out.println("Enter the output");
for(int i=0;i<row;i++)
{
	
	output[i]=sc.nextLine();
}
IDthree idt=new IDthree();

double[] g=idt.gain(input, output);
for(double i:g)
{
	System.out.println(i);
}
	}

}
