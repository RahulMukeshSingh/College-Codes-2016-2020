public class ceaserCipher {
public String encrypt(String plain,int shift)
{
	String key="";
	for (int i = 0; i < plain.length(); i++) {
	key+=(char)(plain.charAt(i)+shift);
	}
	return key;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ceaserCipher cc=new ceaserCipher();
		String encrypt=cc.encrypt("rahulsinghmukesh@gmail.com", 16);
		String decrypt=cc.encrypt(encrypt, -16);
		System.out.println(encrypt+" "+decrypt);
	}

}
