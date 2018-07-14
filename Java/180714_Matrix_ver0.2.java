package esg;

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
	
	// Norm of vector
	public static double norm(double[] v, double p) {
		double x = 0;
		for (int i=0;i<v.length; i++)
			x += Math.pow(v[i], p);
		x = Math.pow(x, 1/p);
		return x;
		
	}
	
	// Inner product
	public static double dot(double[] u, double[] v) {
		if (u.length != v.length) throw new RuntimeException("유효하지 않은 연산입니다.");
		double x = 0;
		for (int i=0; i<u.length; i++)
			x += u[i]*v[i];
		return x;
	}
	
	// Vector of ones
	public static double[] ones(int n) {
		double[] v = new double[n];
		for (int i=0;i<n ;i++)
			v[i] = 1;
		return v;
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
	
	// Extract diagonal component of matrix
	public static double[][] diag(double[][] A){
		int n = A.length;
		double[][] B = new double[n][n];
		for (int i=0;i<n;i++)
			B[i][i] = A[i][i];
		return B;
	}
	
	// Diagonal matrix of vector
	public static double[][] diag(double[] v){
		int n = v.length;
		double[][] B = new double[n][n];
		for (int i=0;i<n;i++)
			B[i][i] = v[i];
		return B;
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
	// 수정 필요
	/*
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
	*/
	/*
	// Matrix inverse (Cramer's Rule)
 	// 차수가 높아지면 잘 안 되는거 같음
	public static double[][] inverse (double[][] A){
		int n = A.length;
		double[][] B = new double[n][n];
		double[] v = new double[n];
		double[] w;
		
		for (int i=0; i<n; i++) {
			Arrays.fill(v, 0);
			v[i] = 1;
			w = Matrix.cramer(A, v);
			for (int j=0; j<n; j++)
				B[j][i] = w[j];
		}
		return B;
	}
	
	*/
	
	
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
	
	
	
	
	

	// Submatrix
	public static double[][] submatrix(double[][] A, int s, int t) {
		int n = A.length;
		double subA[][];
		
		// 예외처리(1)
		if (s >= n | t >= n)
			return null;
		
		// 예외처리(2)
		if(n == 1) {
			subA = null;
			
		// Submatrix 계산
		} else {
			subA = new double[n-1][n-1];
			for (int i=0; i<n-1; i++)
				for (int j=0; j<n-1; j++) {
					if(i < s & j < t) {
						subA[i][j] = A[i][j]; 
					} else if (i < s & j >= t) {
						subA[i][j] = A[i][j+1];
					} else if (i >= s & j < t) {
						subA[i][j] = A[i+1][j];
					} else if (i >= s & j >= t) {
						subA[i][j] = A[i+1][j+1];
					}
				}
		}
		return subA;
	}
	
	// Matrix determinant
	public static double determinant(double[][] A) {
		int n = A.length;
		double det = 0;
		
		if(n == 1) {
			det = A[0][0];
		} else if(n == 2) {
			det = A[0][0]*A[1][1]-A[0][1]*A[1][0];
		} else {
			for (int i=0; i<n; i++)
			det += Math.pow(-1,i)*A[i][0]*determinant(submatrix(A,i,0));
		}
		return det;
	}
	
	// Cramer's rule
	public static double[] cramer(double[][] A, double[] b) {
		int n = b.length;
		double D = determinant(A);
		double[] x = new double[n];
		double[][] B;
		
		for(int j=0; j<n; j++) {
			B = copy(A);
			for(int i=0; i<n; i++)
				B[i][j] = b[i];
			x[j] = determinant(B)/D;
		}
		return x;
	}
	
	// Vector copy
	public static double[] copy(double[] v) {
		int n = v.length;
		double[] w = new double[n];
		
		for (int i=0; i<n; i++)
			w[i] = v[i];
		return w;
	}
	
	// Matrix copy
	public static double[][] copy(double[][] A) {
		int m = A.length;
		int n = A[0].length;
		double[][] B = new double[m][n];
		
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				B[i][j] = A[i][j];
		
		return B;
	}
	
	// Insert a column of ones on the left
	public static double[][] insert_one(double[][] A){
		int m = A.length;
		int n = A[0].length;
		double[][] B = new double[m][n+1];
		for (int i=0; i<m; i++) {
			B[i][0] = 1;
			for (int j=0; j<n; j++)
				B[i][j+1] = A[i][j];
		}
		return B;
	}
	
	// Main
	public static void main(String args[]) {
		double[][] A = {{0.3,0.52,1,-0.4},{0.5,1,1.9,2.1},{0.1,0.3,0.5,0.33},{-0.5,0.7,0.54,0}};
		double[][] B = Matrix.inverse(A);
	    Matrix.print(A);
	    Matrix.print(B);
	    double[] v = {1,2,3};
	    Matrix.print(diag(v));
	    Matrix.print(diag(A));
	    Matrix.print(insert_one(A));
	    Matrix.print(ones(4));
	}
}