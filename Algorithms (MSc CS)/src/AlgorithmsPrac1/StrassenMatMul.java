package AlgorithmsPrac1;

import java.util.Scanner;

class MatrixFunctions {
	int m = 0, n = 0;

	public int[][] MatrixAdd(int[][] first, int[][] second) {
		int[][] result = new int[first.length][first[0].length];
		for (int i = 0; i < first.length; i++) {
			for (int j = 0; j < first[i].length; j++) {
				result[i][j] = first[i][j] + second[i][j];
			}
		}
		return result;
	}

	public int[][] MatrixSub(int[][] first, int[][] second) {
		int[][] result = new int[first.length][first[0].length];
		for (int i = 0; i < first.length; i++) {
			for (int j = 0; j < first[i].length; j++) {
				result[i][j] = first[i][j] - second[i][j];
			}
		}
		return result;
	}

	public int[][][] MatrixPartition(int[][] matrix) {
		int newLen = (matrix.length / 2);
		int[][][] result = new int[4][newLen][newLen];
		int jChange = 0, kChange = 0;
		for (int i = 0; i < 4; i++) {
			if (i == 2) {
				jChange = newLen;
			}
			if ((i % 2) == 1) {
				kChange = newLen;
			} else {
				kChange = 0;
			}
			for (int j = 0 + jChange; j < newLen + jChange; j++) {
				for (int k = 0 + kChange; k < newLen + kChange; k++) {
					result[i][j - jChange][k - kChange] = matrix[j][k];
				}
			}
		}
		return result;
	}

	public int[][] MatrixCombine(int[][][] matrix) {
		int newLen = (matrix[0].length * 2);
		int[][] result = new int[newLen][newLen];
		int jChange = 0, kChange = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (i == 2) {
				jChange = matrix[i].length;
			}
			if ((i % 2) == 1) {
				kChange = matrix[i].length;
			} else {
				kChange = 0;
			}
			for (int j = 0; j < matrix[i].length; j++) {
				for (int k = 0; k < matrix[i][j].length; k++) {
					result[j + jChange][k + kChange] = matrix[i][j][k];
				}

			}
		}
		return result;
	}

	public int makeMatrixSizeTwoRaiseToK(int size) {
		int k = 0, newSize = 0;
		while (true) {
			if (((int) Math.pow(2, k)) >= size) {
				newSize = (int) Math.pow(2, k);
				break;
			}
			k++;
		}
		return newSize;
	}

	public int[][][] inputMatrix() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the m : ");
		m = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the n : ");
		n = sc.nextInt();
		sc.nextLine();
		int[][][] input = new int[2][makeMatrixSizeTwoRaiseToK(m)][makeMatrixSizeTwoRaiseToK(n)];
		for (int k = 0; k < 2; k++) {
			System.out.println("Enter the Matrix" + (k + 1) + " : ");
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					input[k][i][j] = sc.nextInt();
				}
			}
		}
		return input;
	}

	public void MatrixOutput(int[][][] inputMatrix, int[][] outputMatrix) {
		for (int i = 0; i < inputMatrix.length; i++) {
			System.out.println("Matrix" + (i + 1) + " : ");
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < n; k++) {
					System.out.print(inputMatrix[i][j][k] + "\t");
				}
				System.out.println();
			}
		}
		System.out.println("Output : ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(outputMatrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("T(n) = O(n^2.8074)");
		System.out.println("for n = "+ m);
		System.out.println("T("+m+") = "+Math.pow(m, 2.8074));
	}
}

public class StrassenMatMul {

	MatrixFunctions mf = new MatrixFunctions();

	public int[][] Strassen(int n, int[][] firstMatrix, int[][] secondMatrix) {
		int[][][] c;
		if (n == 2) {
			c = new int[4][1][1];
			c[0][0][0] = (firstMatrix[0][0] * secondMatrix[0][0]) + (firstMatrix[0][1] * secondMatrix[1][0]);
			c[1][0][0] = (firstMatrix[0][0] * secondMatrix[0][1]) + (firstMatrix[0][1] * secondMatrix[1][1]);
			c[2][0][0] = (firstMatrix[1][0] * secondMatrix[0][0]) + (firstMatrix[1][1] * secondMatrix[1][0]);
			c[3][0][0] = (firstMatrix[1][0] * secondMatrix[0][1]) + (firstMatrix[1][1] * secondMatrix[1][1]);
		} else {
			int[][][] part1 = mf.MatrixPartition(firstMatrix);
			int[][][] part2 = mf.MatrixPartition(secondMatrix);
			int[][] p = Strassen((n / 2), mf.MatrixAdd(part1[0], part1[3]), mf.MatrixAdd(part2[0], part2[3]));
			int[][] q = Strassen((n / 2), mf.MatrixAdd(part1[2], part1[3]), part2[0]);
			int[][] r = Strassen((n / 2), part1[0], mf.MatrixSub(part2[1], part2[3]));
			int[][] s = Strassen((n / 2), part1[3], mf.MatrixSub(part2[2], part2[0]));
			int[][] t = Strassen((n / 2), mf.MatrixAdd(part1[0], part1[1]), part2[3]);
			int[][] u = Strassen((n / 2), mf.MatrixSub(part1[2], part1[0]), mf.MatrixAdd(part2[0], part2[1]));
			int[][] v = Strassen((n / 2), mf.MatrixSub(part1[1], part1[3]), mf.MatrixAdd(part2[2], part2[3]));
			c = new int[4][p.length][p.length];
			c[0] = mf.MatrixAdd(mf.MatrixSub(mf.MatrixAdd(p, s), t), v);
			c[1] = mf.MatrixAdd(r, t);
			c[2] = mf.MatrixAdd(q, s);
			c[3] = mf.MatrixAdd(mf.MatrixSub(mf.MatrixAdd(p, r), q), u);
		}
		int[][] result = mf.MatrixCombine(c);
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatrixFunctions mf = new MatrixFunctions();
		int[][][] inputMatrix = mf.inputMatrix();
		int[][] outputMatrix = (new StrassenMatMul()).Strassen(inputMatrix[0].length, inputMatrix[0], inputMatrix[1]);
		mf.MatrixOutput(inputMatrix, outputMatrix);
	}
}
