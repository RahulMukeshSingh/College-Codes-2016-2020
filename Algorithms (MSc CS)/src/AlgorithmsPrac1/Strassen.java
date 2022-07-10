package AlgorithmsPrac1;

import java.util.Scanner;

public class Strassen {

	public int[][][] divide(int[][] a) {
		int len = (a.length / 2);
		int[][] c11 = new int[len][len];
		int[][] c12 = new int[len][len];
		int[][] c21 = new int[len][len];
		int[][] c22 = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				c11[i][j] = a[i][j];
				c12[i][j] = a[i][j + len];
				c21[i][j] = a[i + len][j];
				c22[i][j] = a[i + len][j + len];
			}
		}
		return new int[][][] { c11, c12, c21, c22 };
	}

	public int[][] merge(int[][][] divided, int len) {
		int[][] c = new int[len][len];
		for (int i = 0; i < divided[0].length; i++) {
			for (int j = 0; j < divided[0].length; j++) {
				c[i][j] = divided[0][i][j];
				c[i][j + divided[0].length] = divided[1][i][j];
				c[i + divided[0].length][j] = divided[2][i][j];
				c[i + divided[0].length][j + divided[0].length] = divided[3][i][j];
			}

		}
		return c;
	}

	public int MatSize(int s) {
		int r = 0, ns = 0, count = 0;
		for (;;) {
			if ((s <= (int) Math.pow(2, r))) {
				ns = (int) Math.pow(2, r);
				count++;
				if (count == 1) {
					break;
				}
			}
			r++;
		}
		return ns;
	}

	public int[][] addition(int[][] a, int[][] b) {
		int[][] c = new int[a.length][a.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				c[i][j] = a[i][j] + b[i][j];
			}
		}
		return c;
	}

	public int[][] subtraction(int[][] a, int[][] b) {
		int[][] c = new int[a.length][a.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				c[i][j] = a[i][j] - b[i][j];
			}
		}
		return c;
	}

	public int[][] Strassennn(int n, int[][] a, int[][] b) {
		int[][][] c;
		if (n == 2) {
			c = new int[4][n / 2][n / 2];
			c[0][0][0] = (a[0][0] * b[0][0]) + (a[0][1] * b[1][0]);
			c[1][0][0] = (a[0][0] * b[0][1]) + (a[0][1] * b[1][1]);
			c[2][0][0] = (a[1][0] * b[0][0]) + (a[1][1] * b[1][0]);
			c[3][0][0] = (a[1][0] * b[0][1]) + (a[1][1] * b[1][1]);
		} else {
			int[][][] aDivided = divide(a);
			int[][][] bDivided = divide(b);
			int halfSize = (n / 2);
			int[][] pMat = Strassennn(halfSize, addition(aDivided[0], aDivided[3]), addition(bDivided[0], bDivided[3]));
			int[][] qMat = Strassennn(halfSize, addition(aDivided[2], aDivided[3]), bDivided[0]);
			int[][] rMat = Strassennn(halfSize, aDivided[0], subtraction(bDivided[1], bDivided[3]));
			int[][] sMat = Strassennn(halfSize, aDivided[3], subtraction(bDivided[2], bDivided[0]));
			int[][] tMat = Strassennn(halfSize, addition(aDivided[0], aDivided[1]), bDivided[3]);
			int[][] uMat = Strassennn(halfSize, subtraction(aDivided[2], aDivided[0]),
					addition(bDivided[0], bDivided[1]));
			int[][] vMat = Strassennn(halfSize, subtraction(aDivided[1], aDivided[3]),
					addition(bDivided[2], bDivided[3]));
			int size = pMat.length;
			c = new int[4][size][size];
			c[0] = addition(subtraction(addition(pMat, sMat), tMat), vMat);
			c[1] = addition(rMat, tMat);
			c[2] = addition(qMat, sMat);
			c[3] = addition(subtraction(addition(pMat, rMat), qMat), uMat);
		}
		int[][] ans = merge(c, (c[0].length * 2));
		return ans;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Strassen s = new Strassen();
		Scanner sc = new Scanner(System.in);
		int m = 0, n = 0;
		System.out.println("m : ");
		m = sc.nextInt();
		sc.nextLine();
		System.out.println("n : ");
		n = sc.nextInt();
		sc.nextLine();
		int[][] a = new int[s.MatSize(m)][s.MatSize(n)];
		int[][] b = new int[s.MatSize(m)][s.MatSize(n)];

		System.out.println("Enter the First Matrix : ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = sc.nextInt();
			}
		}
		System.out.println("Enter the Second Matrix : ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				b[i][j] = sc.nextInt();
			}
		}
		
		System.out.println("First Matrix : ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("Second Matrix : ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(b[i][j] + "\t");
			}
			System.out.println();
		}
		int[][] c = s.Strassennn(a.length, a, b);
		System.out.println("Answer : ");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(c[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("T(n) = O(n^2.8074)");
		System.out.println("for n = " + n + "," + " T(" + n + ") = " + Math.pow(m, 2.8074));
	}
}
