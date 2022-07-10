
public class simplecolumnar {
	public String encrypt(String plain, int[] order, int size) {
		String encryptt = "";
		int len = plain.length();
		int col = size;
		int row = len / col;
		if ((len % col) != 0) {
			row += 1;
		}
		String[][] encryptMatrix = new String[row][col];
		int k = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {

				if (k < len) {
					encryptMatrix[i][j] = "" + plain.charAt(k);
				} else {
					encryptMatrix[i][j] = "*";
				}
				k++;
			}
		}

		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				encryptt += encryptMatrix[j][order[i]];
			}
		}
		System.out.println("Encrypted Text : " + encryptt.replace("*", ""));
		return encryptt;
	}

	public String decrypt(String encryptedText, int[] order, int size) {
		String decryptt = "";
		int len = encryptedText.length();
		int row = size;
		int col = len / row;
		String[][] decryptMatrix = new String[row][col];
		int k = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				decryptMatrix[order[i]][j] = "" + encryptedText.charAt(k);
				k++;
			}
		}

		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				decryptt += decryptMatrix[j][i];
			}
		}
		return decryptt.replace("*", "");
	}

	public static void main(String[] args) {
		String plain = "hello world";
		int[] order = new int[] { 2, 0, 1 };
		int size = 3;
		simplecolumnar sc = new simplecolumnar();
		String encryptedText = sc.encrypt(plain, order, size);
		String decyptedText = sc.decrypt(encryptedText, order, size);
		System.out.println("Decrypted Text : " + decyptedText);
	}

}
