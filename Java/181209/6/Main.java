package com.tutorial.main;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Main {

	public static void main(String[] args) {
		double[][] data = {{1d,2d,3d},{2d,5d,3d}};
		RealMatrix A = MatrixUtils.createRealMatrix(data);
		System.out.println(A);
		
		double[][] data2  = {{1d,2d},{2d,5d},{1d,7d}};
		RealMatrix B = MatrixUtils.createRealMatrix(data2);
		System.out.println(B);
		
		RealMatrix C = A.multiply(B);
		System.out.println(C);
		
		System.out.println(C.getRowDimension());
		System.out.println(C.getColumnDimension());
		
		RealMatrix D = new LUDecomposition(C).getSolver().getInverse();
		System.out.println(D);
		
		RealVector constants = new ArrayRealVector(new double[] {1,-2,1}, false);
		System.out.println(constants);
		
		System.out.println(constants.getEntry(0));
		System.out.println(constants.getEntry(1));
		System.out.println(constants.getEntry(2));
		
		RealMatrix E = new Array2DRowRealMatrix(new double[][] {{2,3,-3}, {-1,8,6}, {1,-3,-5}}, false);
		System.out.println(E);
		
		EigenDecomposition eigen = new EigenDecomposition(E);
		System.out.println(eigen.getRealEigenvalues()[0]);
		System.out.println(eigen.getRealEigenvalues()[1]);
		System.out.println(eigen.getRealEigenvalues()[2]);
		
		System.out.println(eigen.getEigenvector(0));
		System.out.println(eigen.getEigenvector(1));
		System.out.println(eigen.getEigenvector(2));
		
	}
}

