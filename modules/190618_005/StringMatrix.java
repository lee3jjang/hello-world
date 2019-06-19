package esg;

public class StringMatrix {
	private String[][] data;
	
	// Initialization with data
	public StringMatrix(String[][] data) {
		int m = data.length;
		int n = data[0].length;
		this.data = new String[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				this.data[i][j] = data[i][j];
	}
	
	// Set Entry
	public void setEntry(int i, int j, String value) {
		this.data[i][j] = value;
	}
	
	// Set
	public void set(String value) {
		int m = this.data.length;
		int n = this.data[0].length; 
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				this.data[i][j] = value;
	}
	
	// Add to Entry
	public void addToEntry(int i, int j, String value) {
		this.data[i][j] += value;
	}
	
	// Get Copy
	public StringMatrix copy() {
		return new StringMatrix(this.data.clone());
	}
	
	// Get Entry
	public String getEntry(int i, int j) {
		return this.data[i][j];
	}
	
	// Get Data
	public String[][] getData() {
		return this.data.clone();
	}
	
	// Matrix Addition
	public StringMatrix add(StringMatrix A) {
		int m = A.getData().length;
		int n = A.getData()[0].length;
		String[][] B = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = this.data[i][j] + A.getEntry(i, j); 
		return new StringMatrix(B);
	}
	
	// Map
	public StringMatrix map(UnivariateFunction<String> fn) {
		int m = this.data.length;
		int n = this.data[0].length;
		String[][] B = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = fn.value(this.data[i][j]);
		return new StringMatrix(B);
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
	public StringMatrix transpose() {
		int m = this.data.length;
		int n = this.data[0].length;
		String[][] B = new String[n][m];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				B[j][i] = this.data[i][j];
		return new StringMatrix(B);
	}
	
	// Set Row Vector
	public void setRowVector(int i, StringVector v) {
		int n = v.getDimension();
		for(int j=0; j<n; j++)
			this.data[i][j] = v.getEntry(j);
	}
	
	// Set Column Vector
	public void setColumnVector(int j, StringVector v) {
		int n = v.getDimension();
		for(int i=0; i<n; i++)
			this.data[i][j] = v.getEntry(i);
	}
	
	// Get Row Vector
	public StringVector getRowVector(int i) {
		int n = this.data[0].length;
		String[] w = new String[n];
		for(int j=0; j<n; j++)
			w[j] = this.data[i][j];
		return new StringVector(w);
	}
	
	// Get Column Vector
	public StringVector getColumnVector(int j) {
		int m = this.data.length;
		String[] w = new String[m];
		for(int i=0; i<m; i++)
			w[i] = this.data[i][j];
		return new StringVector(w);
	}
	
	// Add Row Vector
	public void addRowVector(int k, StringVector v) {
		int m = this.data.length;
		int n = this.data[0].length;
		String[][] A = new String[m+1][n];
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
	public void addColumnVector(int k, StringVector v) {
		int m = this.data.length;
		int n = this.data[0].length;
		String[][] A = new String[m][n+1];
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
		String[][] A = new String[m-1][n];
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
		String[][] A = new String[m][n-1];
		for(int i=0; i<m; i++) {
			for(int j=0; j<k; j++)
				A[i][j] = this.data[i][j];
			for(int j=k; j<n-1; j++)
				A[i][j] = this.data[i][j+1];
		}
		this.data = A;
	}
	
	// Column Sum of Matrix
	public StringVector sumColumnVector() {
		int n = this.data[0].length;
		String[] w = new String[n];
		for(int j=0; j<n; j++)
			w[j] = this.getColumnVector(j).sum();
		return new StringVector(w);
	}
	
	// Row Sum of Matrix
	public StringVector sumRowVector() {
		int m = this.data.length;
		String[] w = new String[m];
		for(int i=0; i<m; i++)
			w[i] = this.getRowVector(i).sum();
		return new StringVector(w);
	}
	
	
	/* Matrix Utilities */
	
	// Zero Matrix of m x n	
	public static StringMatrix createZeroMatrix(int m, int n) {
		StringMatrix A = new StringMatrix(new String[m][n]);
		A.set("");
		return A;
	}
	
	// Zero Matrix of n	
	public static StringMatrix createZeroMatrix(int n) {
		return createZeroMatrix(n, n);
	}
	
	// Concatenate(row)
	public static StringMatrix concatenateRowVector(StringVector[] x) {
		int m = x.length;
		int n = x[0].getDimension();
		StringMatrix M = createZeroMatrix(m, n);
		for(int i=0; i<m; i++)
			M.setRowVector(i, x[i]);
		return M;
	}
	
	// Concatenate(Column)
	public static StringMatrix concatenateColumnVector(StringVector[] x) {
		int m = x[0].getDimension();
		int n = x.length;
		StringMatrix M = createZeroMatrix(m, n);
		for(int j=0; j<n; j++)
			M.setColumnVector(j, x[j]);
		return M;
	}
	
	
}
