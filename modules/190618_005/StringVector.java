package esg;

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
	public StringVector map(UnivariateFunction<String> fn) {
		int n = this.data.length;
		String[] v = new String[n];
		for(int i=0; i<n; i++)
			v[i] = fn.value(this.data[i]);
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
	
	
	/* Vector Utilities */
	
	// Zero Vector of n	
	public static StringVector createZeroVector(int n) {
		StringVector w = new StringVector(new String[n]);
		w.set("");
		return w;
	}
	
}
