package esg;

import java.util.Arrays;

public class Matrix {
	
	// Print matrix
	public static void print(double[][] A) {
		int m = A.length;
		int n = A[0].length;
		System.out.println("===print===");
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				System.out.print(A[i][j]+" ");
			}
			System.out.println();
		}
	}
		
	// Print vector
	public static void print(double[] v) {
		int m = v.length;
		System.out.println("===print===");
		for (int i=0; i<m; i++)
			System.out.print(v[i]+" ");
		System.out.println();
	}
	
	// Inner product
	public static double dot(double[] u, double[] v) {
		if (u.length != v.length) throw new RuntimeException("유요하지 않은 연산입니다.");
		double x = 0;
		for (int i=0; i<u.length; i++)
			x += u[i]*v[i];
		return x;
	}
	
	// Transpose of vector
	public static double[][] transpose(double[] v) {
		int n = v.length;
		double[][] w = new double[n][1];
		for (int i=0; i<n; i++)
			w[i][0] = v[i];
		return w;
	}
	
	// Scalar multiplication of Vector
	public static double[] scalar(double c, double[] v) {
		int n = v.length;
		double[] w = new double[n];
		for (int i=0; i<n; i++)
			w[i] = c*v[i];
		return w;
	}
	
	// Vector addition
	public static double[] add(double[] u, double[] v) {
		int n = u.length;
		double[] w = new double[n];
		for (int i=0; i<n; i++)
			w[i] = u[i] + v[i];
		return w;
	}
	
	// Vector subtraction
	public static double[] subtract(double[] u, double[] v) {
		return add(u, scalar(-1, v));
	}
	
	
	// Identity matrix
	public static double[][] identity(int n){
		double[][] A = new double[n][n];
		for(int i=0; i<n; i++)
			A[i][i] = 1;
		return A;
	}
	
	// Transpose of matrix
	public static double[][] transpose(double[][] A) {
		int m = A.length;
		int n = A[0].length;
		double[][] B = new double[n][m];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				B[j][i] = A[i][j];
		return B;
		
	}
	
	// Scalar multiplication of Matrix
	public static double[][] scalar(double c, double[][] A) {
		int m = A.length;
		int n = A[0].length;
		double[][] C = new double[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				C[i][j] = c*A[i][j];
		return C;
	}
	
	// Matrix addition
	public static double[][] add(double[][] A, double[][] B) {
		int m = A.length;
		int n = A[0].length;
		double[][] C = new double[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				C[i][j] = A[i][j] + B[i][j];
		return C;
	}
	
	// Matrix subtraction
	public static double[][] subtract(double[][] A, double[][] B) {
		return add(A,scalar(-1,B));
	}
	
	// Matrix multiplication
	public static double[][] multiply(double[][] A, double[][] B) {
		int m = A.length;
		int n = A[0].length;
		int r = B.length;
		int s = B[0].length;
		if (n != r) throw new RuntimeException("유효하지 않은 연산입니다.");
		double[][] C = new double[m][s];
		for (int i=0; i<m; i++)
			for (int j=0; j<s; j++)
				for (int k=0; k<r; k++)
					C[i][j] += A[i][k] * B[k][j];
		return C;
	}
	
	// Matrix-vector multiplication
	public static double[] multiply(double[][] A, double[] v) {
		int n = A.length;
		int m = A[0].length;
		if (m != v.length) throw new RuntimeException("유효하지 않은 연산입니다.");
		double[] w = new double[n];
		for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                w[i] += A[i][j] * v[j];
		return w;
	}
		
	// Solving linear equation (Gauss-Seidel method)
	public static double[] solve(double[][] A, double[] b) {
		int n = b.length;
		double[] x = new double[n];
		int iter = 100;
		double term1;
		double term2;

		// Initialization
		Arrays.fill(x, 0);
		
		// Iteration
		for (int k=0; k<iter; k++) {
			// Next
			for (int i=0; i<n; i++) {
				term1 = 0;
				term2 = 0;
				for (int j=0; j<i; j++)
					term1 += A[i][j]*x[j];
				for (int j=i+1; j<n; j++)
					term2 += A[i][j]*x[j];
				x[i] = 1/A[i][i]*(b[i] - term1 - term2);
			}
		}
		return x;
	}
	
	// Matrix inverse (Gauss-Seidel method)
	public static double[][] inverse2 (double[][] A){
		int n = A.length;
		double[][] B = new double[n][n];
		double[] v = new double[n];
		double[] w;
		
		for (int i=0; i<n; i++) {
			Arrays.fill(v, 0);
			v[i] = 1;
			w = Matrix.solve(A, v);
			for (int j=0; j<n; j++)
				B[j][i] = w[j];
		}
		return B;
	}

	
	// Matrix determinant
	public static double determinant (double[][] matrix) {
		double temporary[][];
		double result = 0;

		if (matrix.length == 1) {
			result = matrix[0][0];
			return (result);
		}

		if (matrix.length == 2) {
			result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
			return (result);
		}

		for (int i = 0; i < matrix[0].length; i++) {
			temporary = new double[matrix.length - 1][matrix[0].length - 1];

			for (int j = 1; j < matrix.length; j++) {
				for (int k = 0; k < matrix[0].length; k++) {
					if (k < i) {
						temporary[j - 1][k] = matrix[j][k];
					} else if (k > i) {
						temporary[j - 1][k - 1] = matrix[j][k];
					}
				}
			}

			result += matrix[0][i] * Math.pow (-1, (double) i) * determinant (temporary);
		}
		return (result);
	}

	// Matrix inverse
	public static double[][] inverse (double[][] matrix) {
		int n=matrix.length;
		double[][] A = new double[n][n];
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				A[i][j] = matrix[i][j];

		double[][] auxiliaryMatrix, invertedMatrix;
		int[] index;

		auxiliaryMatrix = new double[A.length][A.length];
		invertedMatrix = new double[A.length][A.length];
		index = new int[A.length];

		for (int i = 0; i < A.length; ++i) {
			auxiliaryMatrix[i][i] = 1;
		}

		transformToUpperTriangle (A, index);

		for (int i = 0; i < (A.length - 1); ++i) {
			for (int j = (i + 1); j < A.length; ++j) {
				for (int k = 0; k < A.length; ++k) {
					auxiliaryMatrix[index[j]][k] -= A[index[j]][i] * auxiliaryMatrix[index[i]][k];
				}
			}
		}

		for (int i = 0; i < A.length; ++i) {
			invertedMatrix[A.length - 1][i] = (auxiliaryMatrix[index[A.length - 1]][i] / A[index[A.length - 1]][A.length - 1]);

			for (int j = (A.length - 2); j >= 0; --j) {
				invertedMatrix[j][i] = auxiliaryMatrix[index[j]][i];

				for (int k = (j + 1); k < A.length; ++k) {
					invertedMatrix[j][i] -= (A[index[j]][k] * invertedMatrix[k][i]);
				}

				invertedMatrix[j][i] /= A[index[j]][j];
			}
		}

		return (invertedMatrix);
	}
	
	// Transform to upper triangle
	public static void transformToUpperTriangle (double[][] matrix, int[] index) {
		double[] c;
		double c0, c1, pi0, pi1, pj;
		int itmp, k;

		c = new double[matrix.length];

		for (int i = 0; i < matrix.length; ++i) {
			index[i] = i;
		}

		for (int i = 0; i < matrix.length; ++i) {
			c1 = 0;

			for (int j = 0; j < matrix.length; ++j) {
				c0 = Math.abs (matrix[i][j]);

				if (c0 > c1) {
					c1 = c0;
				}
			}

			c[i] = c1;
		}

		k = 0;

		for (int j = 0; j < (matrix.length - 1); ++j) {
			pi1 = 0;

			for (int i = j; i < matrix.length; ++i) {
				pi0 = Math.abs (matrix[index[i]][j]);
				pi0 /= c[index[i]];

				if (pi0 > pi1) {
					pi1 = pi0;
					k = i;
				}
			}

			itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;

			for (int i = (j + 1); i < matrix.length; ++i) {
				pj = matrix[index[i]][j] / matrix[index[j]][j];
				matrix[index[i]][j] = pj;

				for (int l = (j + 1); l < matrix.length; ++l) {
					matrix[index[i]][l] -= pj * matrix[index[j]][l];
				}
			}
		}
	}
}
