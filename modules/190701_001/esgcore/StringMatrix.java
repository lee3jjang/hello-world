package esgcore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


public class StringMatrix {
	private String[][] data;
	private List<String> rowNames;
	private List<String> columnNames;
	
	// Initialization with data
	public StringMatrix(String[][] data) {
		int m = data.length;
		int n = data[0].length;
		this.data = new String[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				this.data[i][j] = data[i][j];
	}
	
	// Initialization with data and names
	public StringMatrix(String[][] data, List<String> rowNames, List<String> columnNames) {
		int m = data.length;
		int n = data[0].length;
		this.data = new String[m][n];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				this.data[i][j] = data[i][j];
		this.rowNames = new ArrayList<>(rowNames);
		this.columnNames = new ArrayList<>(columnNames);
	}
	
	// Get Row Names
	public List<String> getRowNames() {
		return this.rowNames;
	}

	// Set Row Names
	public void setRowNames(List<String> v) {
		this.rowNames = new ArrayList<>(v);
	}
	
	// Get Column Names
	public List<String> getColumnNames() {
		return this.columnNames;
	}
	
	// Set Column Names
	public void setColumnNames(List<String> v) {
		this.columnNames = new ArrayList<>(v);
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
	
	// Get Entry using Name
	public String getEntry(String rowName, String columnName) {
		return this.getEntry(this.getRowNames().indexOf(rowName), this.getColumnNames().indexOf(columnName));
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
	public StringMatrix map(UnaryOperator<String> fn) {
		int m = this.data.length;
		int n = this.data[0].length;
		String[][] B = new String[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				B[i][j] = fn.apply(this.data[i][j]);
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
	
	// Get Row Vector using Name
	public StringVector getRowVector(String rowName) {
		return this.getRowVector(this.getRowNames().indexOf(rowName));
	}
	
	// Get Column Vector
	public StringVector getColumnVector(int j) {
		int m = this.data.length;
		String[] w = new String[m];
		for(int i=0; i<m; i++)
			w[i] = this.data[i][j];
		return new StringVector(w);
	}
	
	// Get Column Vector using Name
	public StringVector getColumnVector(String columnName) {
		return this.getColumnVector(this.getColumnNames().indexOf(columnName));
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
	
	// String to double
	public Matrix toMatrix() {
		int m = this.data.length;
		int n = this.data[0].length;
		double[][] A = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				A[i][j] = Double.parseDouble(this.data[i][j]);
		return new Matrix(A);
	}
	
	// StringMatrix to StringVector
	public StringVector toStringVector() {
		int m = this.data.length;
		int n = this.data[0].length;
		String[] w = new String[n*m];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				w[n*i+j] = this.data[i][j];
		return new StringVector(w);
	}
	
	// Pivot Table Sum
	public Matrix pivotTableSum(int index, int column, int values) {
			// Index List
			List<String> indexList = Arrays.stream(this.getData())
					.map(x -> x[index])
					.distinct().collect(Collectors.toList());
			
			// Column List			
			List<String> columnList = Arrays.stream(this.getData())
					.map(x -> x[column])
					.distinct().collect(Collectors.toList());

			// Aggregated Values
			Map<List<String>, Double> str = Arrays.stream(this.getData()).collect(Collectors.groupingBy(df -> {
				List<String> indexColumn = new ArrayList<>();
				indexColumn.add(df[index]);
				indexColumn.add(df[column]);
				return indexColumn;
			}, Collectors.summingDouble(df -> Double.parseDouble(df[values]))));
			
			// Set Result
			int m = indexList.size();
			int n = columnList.size();
			double[][] A = new double[m][n];
			for(int i=0; i<m; i++)
				for(int j=0; j<n; j++)
					A[i][j] = str.get(Arrays.asList(indexList.get(i), columnList.get(j))); 
		
			return new Matrix(A, indexList, columnList);
	}
	
	// Pivot Table Average
	public Matrix pivotTableAvg(int index, int column, int values) {
		// Index List
		List<String> indexList = Arrays.stream(this.getData())
				.map(x -> x[index])
				.distinct().collect(Collectors.toList());
		
		// Column List			
		List<String> columnList = Arrays.stream(this.getData())
				.map(x -> x[column])
				.distinct().collect(Collectors.toList());

		// Aggregated Values
		Map<List<String>, Double> str = Arrays.stream(this.getData()).collect(Collectors.groupingBy(df -> {
			List<String> indexColumn = new ArrayList<>();
			indexColumn.add(df[index]);
			indexColumn.add(df[column]);
			return indexColumn;
		}, Collectors.averagingDouble(df -> Double.parseDouble(df[values]))));
		
		// Set Result
		int m = indexList.size();
		int n = columnList.size();
		double[][] A = new double[m][n];
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				A[i][j] = str.get(Arrays.asList(indexList.get(i), columnList.get(j))); 
	
		return new Matrix(A, indexList, columnList);
	}
	
	// Sort Row Vectors by Column
	public void sortRowVector(int j) {
		Arrays.sort(this.data, (v, w) -> v[j].compareTo(w[j]));
	}

	// Sort Row Vectors by Columns
	public void sortRowVector(int[] columns) {
		Arrays.sort(this.data, (v, w) -> {
			int value = 0;
			int n = columns.length;
			for(int j=0; j<n; j++) {
				value += v[columns[j]].compareTo(w[columns[j]])*Math.pow(10, n-j-1);
			}
			return value;
		});
	}

	// Sort Row Vectors by Columns
	public void sortRowVector(int[] columns, Comparator<String> comp) {
		Arrays.sort(this.data, (v, w) -> {
			int value = 0;
			int n = columns.length;
			for(int j=0; j<n; j++) {
				value += comp.compare(v[columns[j]], w[columns[j]])*Math.pow(10, n-j-1);
			}
			return value;
		});
	}
	
	// Filter Row Vector
	public StringMatrix filterRowVector(Predicate<StringVector> fn) {
		int m = this.data.length;
		List<String[]> A = new ArrayList<>();
		for(int i=0; i<m; i++)
			if(fn.test(this.getRowVector(i)))
				A.add(this.data[i]);
		return new StringMatrix(A.toArray(new String[0][0]));
	}

	// Filter Row Vector (2)
	public StringMatrix filterRowVector(int j, String value) {
		return this.filterRowVector(v -> v.getEntry(j).equals(value));
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
