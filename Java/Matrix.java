package economic_scenario_generator;
import java.util.Arrays;

public class Matrix {
	public static double[][] identity(int n){
		double[][] A = new double[n][n];
		for(int i=0; i<n; i++)
			A[i][i] = 1;
		return A;
	}
	
	public static double dot(double[] u, double[] v) {
		if (u.length != v.length) throw new RuntimeException("유요하지 않은 연산입니다.");
		double sum = 0.0;
		for (int i=0; i<u.length; i++)
			sum += u[i]*v[i];
		return sum;
	}
	
	public static double[][] transpose(double[][] A) {
		int m = A.length;
		int n = A[0].length;
		double[][] B = new double[n][m];
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				B[j][i] = A[i][j];
		return B;
		
	}
	
	public static void print(double[][] A) {
		int m = A.length;
		int n = A[0].length;
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				System.out.print(A[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String args[]) {
		double[][] A = Matrix.identity(3);
		System.out.println(A.length);
		
		double[] x = {4,2,3};
		double[] y = {4,2,-5};
		double res = Matrix.dot(x, y);
		System.out.println(res);
		
		double[][] B = {{1,4,2}, {-4,2,0}};
		double[][] tB = Matrix.transpose(B);
		Matrix.print(tB);
		
	}
	
}
