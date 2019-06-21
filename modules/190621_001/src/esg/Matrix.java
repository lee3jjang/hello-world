package esg;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Matrix {
	private double[][] data;
	
	// Initialization with data
	public Matrix(double[][] data) {
		int m = data.length;
		int n = data[0].length;
		this.data = new double[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				this.data[i][j] = data[i][j];
	}
	
	// Set Entry
	public void setEntry(int i, int j, double value) {
		this.data[i][j] = value;
	}
	
	// Set
	public void set(double value) {
		int m = this.data.length;
		int n = this.data[0].length; 
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				this.data[i][j] = value;
	}
	
	// Add to Entry
	public void addToEntry(int i, int j, double value) {
		this.data[i][j] += value;
	}
	
	// Get Copy
	public Matrix copy() {
		return new Matrix(this.data.clone());
	}
	
	// Get Entry
	public double getEntry(int i, int j) {
		return this.data[i][j];
	}
	
	// Get Data
	public double[][] getData() {
		return this.data.clone();
	}
	
	// Matrix Addition
	public Matrix add(Matrix A) {
		int m = A.getData().length;
		int n = A.getData()[0].length;
		double[][] B = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = this.data[i][j] + A.getEntry(i, j); 
		return new Matrix(B);
	}
	
	// Matrix Subtraction
	public Matrix subtract(Matrix A) {
		return this.add(A.scalarMultiply(-1.));
	}
	
	// Scalar Multiplication of Matrix
	public Matrix scalarMultiply(double c) {
		return this.map(x -> x*c);
	}
	
	// Scalar Addition of Matrix
	public Matrix scalarAdd(double c) {
		return this.map(x -> x+c);
	}
	
	// Map
	public Matrix map(UnaryOperator<Double> fn) {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] B = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = fn.apply(this.data[i][j]);
		return new Matrix(B);
	}
	
	// Sum
	public double sum() {
		int m = this.data.length;
		int n = this.data[0].length;
		double value = 0.;
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				value += this.data[i][j];
		return value;
	}
	
	// Element-Wise Matrix Multiplication
	public Matrix eleMultiply(Matrix A) {
		int m = A.getData().length;
		int n = A.getData()[0].length;
		double[][] B = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = this.data[i][j] * A.getEntry(i, j); 
		return new Matrix(B);
	}
	
	// Element-Wise Matrix Division
	public Matrix eleDivide(Matrix A) {
		int m = A.getData().length;
		int n = A.getData()[0].length;
		double[][] B = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = this.data[i][j] / A.getEntry(i, j); 
		return new Matrix(B);
	}
	
	// Power of Matrix
	public Matrix power(int p) {
		return this.map(x -> Math.pow(x, p));
	}
	
	// Print Matrix
	public void print() {
		int m = this.data.length;
		int n = this.data[0].length;
		System.out.println("===print===");
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				System.out.print(this.data[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	// Get Row Dimension
	public int getRowDimension() {
		return this.data.length; 
	}
	
	// Get Column Dimension
	public int getColumnDimension() {
		return this.data[0].length;
	}
	
	// Transpose of Matrix
	public Matrix transpose() {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] B = new double[n][m];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				B[j][i] = this.data[i][j];
		return new Matrix(B);
	}
	
	// Trace of Matrix
	public double getTrace() {
		int n = this.data.length;
		double value = 0.;
		for(int i=0; i<n; i++)
			value += this.data[i][i];
		return value;
	}
	
	// Determinant of Matrix
	public double determinant() {
		Matrix temporary;
		double result = 0;
		int m = this.data.length;
		int n = this.data[0].length;
		if (m == 1) {
			result = this.data[0][0];
			return (result);
		}
		if (m == 2) {
			result = ((this.data[0][0] * this.data[1][1]) - (this.data[0][1] * this.data[1][0]));
			return (result);
		}
		for (int i = 0; i < n; i++) {
			temporary = new Matrix(new double[m-1][n-1]);
			for (int j = 1; j < m; j++) {
				for (int k = 0; k < n; k++) {
					if (k < i) {
						temporary.setEntry(j-1, k, this.data[j][k]);
					} else if (k > i) {
						temporary.setEntry(j-1, k-1, this.data[j][k]);
					}
				}
			}
			result += this.data[0][i] * Math.pow (-1, (double) i) * temporary.determinant();
		}
		return (result);
	}
	
	// Diagonal Component of Matrix
	public Vector diag() {
		int n = this.data.length;
		double[] w = new double[n];
		for(int i=0; i<n; i++)
			w[i] = this.data[i][i];
		return new Vector(w);
	}
	
	// Multiplication of Matrix
	public Matrix multiply(Matrix A) {
		int m = this.data.length;
		int n = this.data[0].length;
		int r = A.getData().length;
		int s = A.getData()[0].length;
		if (n != r) throw new RuntimeException("유효하지 않은 연산입니다.");
		double[][] B = new double[m][s];
		for (int i=0; i<m; i++)
			for (int j=0; j<s; j++)
				for (int k=0; k<r; k++)
					B[i][j] += this.data[i][k] * A.getEntry(k, j);
		return new Matrix(B);
	}
	
	// Matrix Inverse
	public Matrix inverse () {
		int n = this.data.length;
		double[][] A = new double[n][n];
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				A[i][j] = this.data[i][j];
		double[][] auxiliaryMatrix, invertedMatrix;
		int[] index;
		auxiliaryMatrix = new double[this.data.length][this.data.length];
		invertedMatrix = new double[this.data.length][this.data.length];
		index = new int[A.length];
		for (int i = 0; i < A.length; ++i)
			auxiliaryMatrix[i][i] = 1;
		transformToUpperTriangle (A, index);
		for (int i = 0; i < (A.length - 1); ++i)
			for (int j = (i + 1); j < A.length; ++j)
				for (int k = 0; k < A.length; ++k)
					auxiliaryMatrix[index[j]][k] -= A[index[j]][i] * auxiliaryMatrix[index[i]][k];
		for (int i = 0; i < A.length; ++i) {
			invertedMatrix[A.length - 1][i] = (auxiliaryMatrix[index[A.length - 1]][i] / A[index[A.length - 1]][A.length - 1]);
			for (int j = (A.length - 2); j >= 0; --j) {
				invertedMatrix[j][i] = auxiliaryMatrix[index[j]][i];
				for (int k = (j + 1); k < A.length; ++k)
					invertedMatrix[j][i] -= (A[index[j]][k] * invertedMatrix[k][i]);
				invertedMatrix[j][i] /= A[index[j]][j];
			}
		}
		return new Matrix(invertedMatrix);
	}
	
	// (Private) Transform to upper triangle
	public static void transformToUpperTriangle (double[][] matrix, int[] index) {
		double[] c;
		double c0, c1, pi0, pi1, pj;
		int itmp, k;
		c = new double[matrix.length];
		for (int i = 0; i < matrix.length; ++i)
			index[i] = i;
		for (int i = 0; i < matrix.length; ++i) {
			c1 = 0;
			for (int j = 0; j < matrix.length; ++j) {
				c0 = Math.abs (matrix[i][j]);
				if (c0 > c1) c1 = c0;
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
				for (int l = (j + 1); l < matrix.length; ++l)
					matrix[index[i]][l] -= pj * matrix[index[j]][l];
			}
		}
	}
	
	// Matrix Operation to Vector
	public Vector operate(Vector v) {
		int m = this.data.length;
		int n = this.data[0].length;
		int r = v.getData().length;
		if (n != r) throw new RuntimeException("유효하지 않은 연산입니다.");
		double[] w = new double[m];
		for (int i=0; i<m; i++)
			for (int k=0; k<n; k++)
					w[i] += this.data[i][k] * v.getEntry(k);
		return new Vector(w);
	}
	
	// Cholesky Decomposition
	public Matrix cholesky() {
	    int n  = this.data.length;
	    double[][] L = new double[n][n];
	    for (int i = 0; i < n; i++)  {
	        for (int j = 0; j <= i; j++) {
	            double sum = 0.0;
	            for (int k = 0; k < j; k++) {
	                sum += L[i][k] * L[j][k];
	            }
	            if (i == j)
	            	L[i][i] = Math.sqrt(this.data[i][i] - sum);
	            else
	            	L[i][j] = 1.0 / L[j][j] * (this.data[i][j] - sum);
	        }
	        if (L[i][i] <= 0) {
	            throw new RuntimeException("Matrix not positive definite");
	        }
	    }
	    return new Matrix(L);
	}
	
	// Set Row Vector
	public void setRowVector(int i, Vector v) {
		int n = v.getDimension();
		for(int j=0; j<n; j++)
			this.data[i][j] = v.getEntry(j);
	}
	
	// Set Column Vector
	public void setColumnVector(int j, Vector v) {
		int n = v.getDimension();
		for(int i=0; i<n; i++)
			this.data[i][j] = v.getEntry(i);
	}
	
	// Get Row Vector
	public Vector getRowVector(int i) {
		int n = this.data[0].length;
		double[] w = new double[n];
		for(int j=0; j<n; j++)
			w[j] = this.data[i][j];
		return new Vector(w);
	}
	
	// Get Column Vector
	public Vector getColumnVector(int j) {
		int m = this.data.length;
		double[] w = new double[m];
		for(int i=0; i<m; i++)
			w[i] = this.data[i][j];
		return new Vector(w);
	}
	
	// Add Row Vector
	public void addRowVector(int k, Vector v) {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] A = new double[m+1][n];
		for(int j=0; j<n; j++) {
			for(int i=0; i<k; i++)
				A[i][j] = this.data[i][j];
			A[k][j] = v.getEntry(j);
			for(int i=k+1; i<m+1; i++)
				A[i][j] = this.data[i-1][j];
		}
		this.data = A;
	}
	
	// Add Column Vector
	public void addColumnVector(int k, Vector v) {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] A = new double[m][n+1];
		for(int i=0; i<m; i++) {
			for(int j=0; j<k; j++)
				A[i][j] = this.data[i][j];
			A[i][k] = v.getEntry(i);
			for(int j=k+1; j<n+1; j++)
				A[i][j] = this.data[i][j-1];
		}
		this.data = A;
	}
	// Delete Row Vector
	public void deleteRowVector(int k) {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] A = new double[m-1][n];
		for(int j=0; j<n; j++) {
			for(int i=0; i<k; i++)
				A[i][j] = this.data[i][j];
			for(int i=k; i<m-1; i++)
				A[i][j] = this.data[i+1][j];
		}
		this.data = A;
	}
	
	// Delete Column Vector
	public void deleteColumnVector(int k) {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] A = new double[m][n-1];
		for(int i=0; i<m; i++) {
			for(int j=0; j<k; j++)
				A[i][j] = this.data[i][j];
			for(int j=k; j<n-1; j++)
				A[i][j] = this.data[i][j+1];
		}
		this.data = A;
	}
	
	// Apply Function to Row Vector
	public Vector applyRowVector(Function<Vector, Double> fn) {
		int m = this.data.length;
		double[] w = new double[m];
		for(int i=0; i<m; i++)
			w[i] = fn.apply(this.getRowVector(i));
		return new Vector(w);
	}
	
	// Apply Function to Column Vector
	public Vector applyColumnVector(Function<Vector, Double> fn) {
		int n = this.data[0].length;
		double[] w = new double[n];
		for(int j=0; j<n; j++)
			w[j] = fn.apply(this.getColumnVector(j));
		return new Vector(w);
	}
	
	// Column Sum of Matrix
	public Vector sumColumnVector() {
		return this.applyColumnVector(x -> x.sum());
	}
	
	// Row Sum of Matrix
	public Vector sumRowVector() {
		return this.applyRowVector(x -> x.sum());
	}
	
	// Column Mean of Matrix
	public Vector meanColumnVector() {
		return this.applyColumnVector(x -> x.mean());
	}
	
	// Row Mean of Matrix
	public Vector meanRowVector() {
		return this.applyRowVector(x -> x.mean());
	}
	
	// Column Standard Deviation of Matrix
	public Vector stdColumnVector() {
		return this.applyColumnVector(x -> x.std());
	}
	
	// Row Standard Deviation of Matrix
	public Vector stdRowVector() {
		return this.applyRowVector(x -> x.std());
	}
	
	// Concatenate Matrix by Row
	public Matrix concatenateByRow(Matrix A) {
		int m = this.data.length;
		int n = this.data[0].length;
		int r = A.getRowDimension();
		int s = A.getColumnDimension();
		if(n != s)
			throw new RuntimeException("유효하지 않은 열 길이입니다.");
		double[][] B = new double[m+r][n];
		for(int j=0; j<n; j++) {
			for(int i=0; i<m; i++)
				B[i][j] = this.data[i][j];
			for(int i=0; i<r; i++)
				B[m+i][j] = A.getEntry(i, j);
		}
		return new Matrix(B);
	}
	
	// Concatenate Matrix by Column
	public Matrix concatenateByColumn(Matrix A) {
		int m = this.data.length;
		int n = this.data[0].length;
		int r = A.getRowDimension();
		int s = A.getColumnDimension();
		if(m != r)
			throw new RuntimeException("유효하지 않은 행 길이입니다.");
		double[][] B = new double[m][n+s];
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++)
				B[i][j] = this.data[i][j];
			for(int j=0; j<s; j++)
				B[i][n+j] = A.getEntry(i, j);
		}
		return new Matrix(B);	
	}
	
	// double to String
	public StringMatrix toStringMatrix() {
		int m = this.data.length;
		int n = this.data[0].length;
		String[][] A = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				A[i][j] = String.valueOf(this.data[i][j]);
		return new StringMatrix(A);
	}
	
	// Matrix to Vector
	public Vector toVector() {
		int m = this.data.length;
		int n = this.data[0].length;
		double[] w = new double[n*m];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				w[n*i+j] = this.data[i][j];
		return new Vector(w);
	}
	
	
	/* Matrix Utilities */
	
	// Identity Matrix of n
	public static Matrix createIdentityMatrix(int n) {
		double[][] A = new double[n][n];
		for(int i=0; i<n; i++)
			A[i][i] = 1.;
		return new Matrix(A);
	}
	
	// Zero Matrix of m x n	
	public static Matrix createZeroMatrix(int m, int n) {
		return new Matrix(new double[m][n]);
	}
	
	// Zero Matrix of n	
	public static Matrix createZeroMatrix(int n) {
		return createZeroMatrix(n, n);
	}
	
	// Matrix of Ones of m x n
	public static Matrix createOneMatrix(int m, int n) {
		double[][] A = new double[n][m];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; i++)
				A[i][j] = 1.;
		return new Matrix(A);
	}
	
	// Matrix of Ones of n
	public static Matrix createOneMatrix(int n) {
		return createOneMatrix(n, n);
	}
	
	// Concatenate Row Vectors
	public static Matrix concatenateRowVector(Vector[] x) {
		int m = x.length;
		int n = x[0].getDimension();
		Matrix M = createZeroMatrix(m, n);
		for(int i=0; i<m; i++)
			M.setRowVector(i, x[i]);
		return M;
	}
	
	// Concatenate Column Vectors
	public static Matrix concatenateColumnVector(Vector[] x) {
		int m = x[0].getDimension();
		int n = x.length;
		Matrix M = createZeroMatrix(m, n);
		for(int j=0; j<n; j++)
			M.setColumnVector(j, x[j]);
		return M;
	}
	
}
