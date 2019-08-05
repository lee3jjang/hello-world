package esgcore.linalg;

import java.util.function.UnaryOperator;

public class StringVector {
	private String[] data;

	// Initialization with data
	public StringVector(String[] data) {
		int n = data.length;
		this.data = new String[n];
		for (int i=0; i<n; i++)
				this.data[i] = data[i];
	}
	
	// Set Entry
	public void setEntry(int i, String value) {
		this.data[i] = value;
	}
	
	// Set
	public void set(String value) {
		int n = this.data.length;
		for(int i=0; i<n; i++)
			this.data[i] = value;
	}
	// Add to Entry
	public void addToEntry(int i, String value) {
		this.data[i] += value;
	}
	
	// Get Copy
	public StringVector copy() {
		return new StringVector(this.data.clone());
	}
	
	// Get Entry
	public String getEntry(int i) {
		return this.data[i];
	}
	
	// Get Data
	public String[] getData() {
		return this.data.clone();
	}
	
	// Get Subvector
	public StringVector getSubvector(int r, int s) {
		int n = s-r;
		String[] w = new String[n];
		for(int i=0; i<n; i++)
			w[i] = this.data[r+i];
		return new StringVector(w);
	}
	
	// Set Subvector
	public void setSubvector(int r, StringVector v) {
		int n = v.getDimension();
		for(int i=0; i<n; i++)
			this.data[r+i] = v.getEntry(i);
	}
	
	// Insert Element
	public void insertElement(int k, String value) {
		int n = this.data.length;
		String[] w = new String[n+1];
		for(int i=0; i<k; i++)
			w[i] = this.data[i];
		w[k] = value;
		for(int i=k+1; i<n+1; i++)
			w[i] = this.data[i-1];
		this.data = w;
	}
	
	// Delete Element
	public void deleteElement(int k) {
		int n = this.data.length;
		String[] w = new String[n-1];
		for(int i=0; i<k; i++)
			w[i] = this.data[i];
		for(int i=k; i<n-1; i++)
			w[i] = this.data[i+1];
		this.data = w;
	}
	
	// Get Dimension
	public int getDimension() {
		return this.data.length;
	}
	
	// Vector Addition
	public StringVector add(StringVector v) {
		int n = v.getData().length;
		String[] w = new String[n];
		for(int i=0; i<n; i++)
				w[i] = this.data[i] + v.getEntry(i);
		return new StringVector(w);
	}
	
	// Print Vector
	public void print() {
		int n = this.data.length;
		System.out.println("===print===");
		for (int i=0; i<n; i++)
			System.out.print(this.data[i]+" ");
		System.out.println();
	}
	
	// Map
	public StringVector map(UnaryOperator<String> fn) {
		int n = this.data.length;
		String[] v = new String[n];
		for(int i=0; i<n; i++)
			v[i] = fn.apply(this.data[i]);
		return new StringVector(v);
	}
	
	// Substring
	public StringVector substring(int i, int j) {
		return this.map(s -> s.substring(i, j));
	}
	
	public StringVector substring(int i) {
		return this.map(s -> s.substring(i));
	}
	
	// Replace
	public StringVector replace(String s1, String s2) {
		return this.map(s -> s.replace(s1, s2));
	}
	
	public StringVector replaceFirst(String s1, String s2) {
		return this.map(s -> s.replaceFirst(s1, s2));
	}
	
	// Transpose
	public StringMatrix transpose() {
		int n = this.data.length;
		String[][] B = new String[1][n];
		for (int i=0; i<n; i++)
				B[0][i] = this.data[i];
		return new StringMatrix(B);
	}
	
	// Sum
	public String sum() {
		int n = this.data.length;
		String value = "";
		for(int i=0; i<n; i++)
			value += this.data[i];
		return value;
	}
	
	// Concatenate
	public StringVector concatenate(StringVector v) {
		int n = this.data.length;
		int m = v.getDimension();
		String[] w = new String[n+m];
		for(int i=0; i<n; i++)
			w[i] = this.data[i];
		for(int i=0; i<m; i++)
			w[m+i] = v.getEntry(i);
		return new StringVector(w);
	}
	
	// String to double
	public Vector toVector() {
		int n = this.data.length;
		double[] v = new double[n];
		for(int i=0; i<n; i++)
			v[i] = Double.parseDouble(this.data[i]);
		return new Vector(v);
	}
	
	// StringVector to StringMatrix
	public StringMatrix toStringMatrix(int n) {
		int m = this.data.length/n;
		if(this.data.length%n != 0)
			throw new RuntimeException("유효하지 않은 열 길이입니다.");
		String[][] A = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				A[i][j] = this.data[n*i+j];
		return new StringMatrix(A);
	}
	
	
	/* Vector Utilities */
	
	// Zero Vector of n
	public static StringVector createZeroVector(int n) {
		StringVector w = new StringVector(new String[n]);
		w.set("");
		return w;
	}
	
}
