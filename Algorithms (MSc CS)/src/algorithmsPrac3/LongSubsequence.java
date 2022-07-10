package algorithmsPrac3;

import java.util.Scanner;

public class LongSubsequence {
	public int[][] getSeqMatLen(String[] firstSeq, String[] secondSeq) {
		int[][] lenMatrix = new int[firstSeq.length + 1][secondSeq.length + 1];
		for (int i = 0; i < lenMatrix.length; i++) {
			lenMatrix[i][0] = 0;
		}
		for (int i = 0; i < lenMatrix[0].length; i++) {
			lenMatrix[0][i] = 0;
		}
		for (int i = 1; i < lenMatrix.length; i++) {
			for (int j = 1; j < lenMatrix[i].length; j++) {
				if (firstSeq[i - 1].equalsIgnoreCase(secondSeq[j - 1])) {
					lenMatrix[i][j] = lenMatrix[i - 1][j - 1] + 1;
				} else {
					lenMatrix[i][j] = (lenMatrix[i - 1][j] > lenMatrix[i][j - 1]) ? lenMatrix[i - 1][j] : lenMatrix[i][j - 1];
				}
			}
		}
		return lenMatrix;
	}

	public String getCommonSeq(int[][] lenMatrix, String[] firstSeq, String[] secondSeq) {
		int i = lenMatrix.length - 1, j = lenMatrix[0].length - 1;
		String commonSequence = "";
		while ((i > 0) && (j > 0)) {
			 if (lenMatrix[i][j] == lenMatrix[i][j - 1]) {
				j--;
			}else if (lenMatrix[i][j] == lenMatrix[i - 1][j]) {
				i--;
			} else {
				commonSequence = firstSeq[i - 1] + " " + commonSequence;
				i--;
				j--;
			}
		}
		return commonSequence.trim();
	}

	public void printFormat(String commonSequence, int[][] lenMatrix, String[] firstSeq, String[] secondSeq) {
		System.out.println("Sequence Length Matrix : ");
		System.out.print("\t0\t");
		for (int i = 0; i < lenMatrix[0].length - 1; i++) {
			System.out.print(secondSeq[i] + "\t");
		}
		System.out.print("\n0\t");
		for (int i = 0; i < lenMatrix.length; i++) {
			if (i != 0) {
				System.out.print(firstSeq[i - 1] + "\t");
			}
			for (int j = 0; j < lenMatrix[i].length; j++) {
				System.out.print(lenMatrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("Common Sequence : " + commonSequence);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the first sequence : ");
		String first = sc.nextLine();
		String[] firstSeq = first.split(" ");
		System.out.println("Enter the second sequence : ");
		String second = sc.nextLine();
		String[] secondSeq = second.split(" ");
		LongSubsequence ls = new LongSubsequence();
		int[][] lenMatrix = ls.getSeqMatLen(firstSeq, secondSeq);
		String commonSequence = ls.getCommonSeq(lenMatrix, firstSeq, secondSeq);
		ls.printFormat(commonSequence, lenMatrix, firstSeq, secondSeq);
	}

}
