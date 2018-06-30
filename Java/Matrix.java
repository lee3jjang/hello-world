public class Matrix {
	
	// Identity matrix
	public static double[][] identity(int n){
		double[][] A = new double[n][n];
		for(int i=0; i<n; i++)
			A[i][i] = 1;
		return A;
	}
	
	// Inner product
	public static double dot(double[] u, double[] v) {
		if (u.length != v.length) throw new RuntimeException("유요하지 않은 연산입니다.");
		double sum = 0.0;
		for (int i=0; i<u.length; i++)
			sum += u[i]*v[i];
		return sum;
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
		int m = A.length;
		int n = A[0].length;
		double[][] C = new double[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				C[i][j] = A[i][j] - B[i][j]; 
		return C;
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
			int m = A.length;
			int n = A[0].length;
			if (n != v.length) throw new RuntimeException("유효하지 않은 연산입니다.");
			double[] w = new double[m];
			for (int i = 0; i < n; i++)
	            for (int j = 0; j < m; j++)
	                w[j] += A[i][j] * v[j];
			return w;
		}
		
	// Gauss-Seidel method(미완성)
		public static double[] GaussSeidel(double[][] A, double[] b) {
			int n = b.length;
			double[] x = new double[n];
			//double iter = 100;
			double term1, term2;

			// Initialization
			for (int i=0; i<n; i++)
				x[i] = 0;
			
			// Next
			for (int i=0; i<n; i++) {
				term1 = 0;
				term2 = 0;
				for (int j=0; j<i-1; j++)
					term1 += A[i][j]*x[j];
				for (int j=i+1; i<n; i++)
					term2 += A[i][j]*x[j];
				x[i] = 1/A[i][i]*(b[i] - term1 - term2);
			}
			return x;
		}
	
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
	}
	
	// Main 
	public static void main(String args[]) {
		double[][] A = Matrix.identity(3);
		System.out.println(A.length);
		
		double[] x = {4,2,3};
		double[] y = {4,2,-5};
		double res = Matrix.dot(x, y);
		System.out.println(res);
		
		double[][] B = {{1,4,2}, {-4,2,0}};
		double[][] tB = Matrix.transpose(B);
		Matrix.print(A);
		
		double[][] C;
		C = Matrix.multiply(tB, B);
		Matrix.print(C);
		
		double[] w;
		w = Matrix.multiply(A, x);
		Matrix.print(w);
		
		double[] z;
		z = Matrix.GaussSeidel(C, w);
		Matrix.print(x);
	}
	
}
