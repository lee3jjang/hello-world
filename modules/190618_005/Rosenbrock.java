package esg;

public class Rosenbrock implements MultivariateFunction {
	public double value(Vector point) {
		int n = point.getDimension();
		Vector v = Vector.createZeroVector(n-1);
		Vector w = Vector.createZeroVector(n-1);
		for(int i=0; i<n-1; i++) {
			v.setEntry(i, point.getEntry(i+1));
			w.setEntry(i, point.getEntry(i));
		}
		return v.subtract(w.power(2)).power(2).scalarMultiply(100.).add(w.scalarMultiply(-1.).scalarAdd(1.).power(2)).sum();
	}
}
