
public class monoalphabetic {
	String[] encryptt = new String[] { "@", "*", "?" };
	String[] decryptt = new String[] { "a", "e", "u" };

	public String encrypt(String plain) {
		String encrypt = "";
		for (int i = 0; i < plain.length(); i++) {
			String part = "" + plain.charAt(i);
			for (int j = 0; j < encryptt.length; j++) {
				if (decryptt[j].equals(""+plain.charAt(i))) {
					part = encryptt[j];
					break;
				}
			}
			encrypt += part;
		}
		return encrypt;
	}
	public String decrypt(String enc) {
		String decrypt = "";
		for (int i = 0; i < enc.length(); i++) {
			String part = "" + enc.charAt(i);
			for (int j = 0; j < encryptt.length; j++) {
				if (encryptt[j].equals(""+enc.charAt(i))) {
					part = decryptt[j];
					break;
				}
			}
			decrypt += part;
		}
		return decrypt;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		monoalphabetic ma = new monoalphabetic();
		String enc=ma.encrypt("How are you");
		System.out.println(enc);
		System.out.println(ma.decrypt(enc));
	}

}
