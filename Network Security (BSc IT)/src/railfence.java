
public class railfence {
public String encrypt(String plainText,int size)
{
String encryptt="";
int len=plainText.length();
int row=size;
int col=len/row;
if((len%row)!=0)
{
col+=1;	
}
String[][] encryptMatrix=new String[row][col];
int k=0;
for (int i = 0; i < col; i++) {
	for (int j = 0; j < row; j++) {
		if(k<len)
		{
		encryptMatrix[j][i]=""+plainText.charAt(k);
		}
		else
		{
			encryptMatrix[j][i]="*";	
		}
		k++;
	}
}
for (String[] strings : encryptMatrix) {
	for (String s : strings) {
		System.out.print(s+" ");
	}
	System.out.println();
}
for (int i = 0; i < row; i++) {
	for (int j = 0; j < col; j++) {
		encryptt+=encryptMatrix[i][j];
	}
}
System.out.println("Encrypted Text is "+encryptt.replace("*", ""));
return encryptt;
}
public String decrypt(String encryptedText,int size)
{
	String decryptt="";
	int len=encryptedText.length();
	int row=size;
	int col=len/row;
	if((len%row)!=0)
	{
	col+=1;	
	}
	String[][] decryptMatrix=new String[row][col];
	int k=0;
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			
			decryptMatrix[i][j]=""+encryptedText.charAt(k);
			
			k++;
		}
	}
for (String[] strings : decryptMatrix) {
	for (String s : strings) {
		System.out.print(s+" ");
	}
	System.out.println();
}
for (int i = 0; i < col; i++) {
	for (int j = 0; j < row; j++) {
		decryptt+=decryptMatrix[j][i];
	}
}
System.out.println(decryptt.replace("*", ""));
	return decryptt;
}
	public static void main(String[] args) {
		String plainText="hello world";
		int size=3;
		railfence r=new railfence();
		String encryptedText=r.encrypt(plainText, size);
	r.decrypt(encryptedText, size);
	}

}
