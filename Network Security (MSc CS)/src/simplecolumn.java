import java.util.*;
public class simplecolumn {
public String sct(String input,int col,int[] order)
{
	String encrypt="";
	int len=input.length();
	int row=len/col;
	if((len%col)!=0)
	{
		row+=1;
	}
	char[][] mat=new char[row][col];
	int k=0;
	
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
		{
			if(k>=len)
			{
				mat[i][j]=' ';
			}
			else
			{
			mat[i][j]=input.charAt(k);
			}
			k++;
			
		}
	}
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col;j++)
		{
	System.out.print(mat[i][j]+" ");		
			
		}
		System.out.println();
	}

	for(int i=0;i<col;i++)
	{
		for(int j=0;j<row;j++)
		{
			
	encrypt+=mat[j][order[i]-1];		
			
		}
	
	}
	
	System.out.println("Encryption is "+encrypt.replace(" ", ""));
	//System.out.println(encrypt);
	return encrypt;
}
public void sctdecrypt(String input,int col,int[] order)
{
	String decrypt="";
	int div=input.length()/col;
	int len=input.length();
	int row=len/col;
	if((len%col)!=0)
	{
		row+=1;
	}
	
	char[][] mat=new char[col][div];
	int k=0;
	
	for(int i=0;i<col;i++)
	{
		for(int j=0;j<div;j++)
		{
					mat[order[i]-1][j]=input.charAt(k);
		
			k++;
			
		}
	}
	for(int i=0;i<col;i++)
	{
		for(int j=0;j<div;j++)
		{
	System.out.print(mat[i][j]+" ");		
			
		}
		System.out.println();
	}

	for(int i=0;i<div;i++)
	{
		for(int j=0;j<col;j++)
		{
	decrypt+=mat[j][i];		
			
		}
		
	}
	System.out.println("Decryption : "+decrypt.replace(" ", ""));
}
	public static void main(String[] args) {
		simplecolumn s=new simplecolumn();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Plain Text:");
		String input=sc.nextLine();
		
		System.out.println("Enter the size:");
		int col=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the order:");
		int[] order=new int[col];
		for(int i=0;i<col;i++)
		{
			order[i]=sc.nextInt();
			sc.nextLine();
		}
		String en=s.sct(input,col,order);
		System.out.println();
		s.sctdecrypt(en, col, order);
		
	}

}
