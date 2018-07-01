package esg;

public class Exercise {
	public static void main(String args[]) {
		Exercise ex = new Exercise();
		mathop addition = (int a, int b) -> a+b;
		mathop multiply = (int a, int b) -> {
			return a*b*4;
		};
		System.out.println("Hello world!");
		int x = ex.operate(4,2, addition);
		int y = ex.operate(4,2, multiply);
		System.out.println(x);
		System.out.println(y);
	}
	
	interface mathop{
		int aaa(int a, int b);
	}
	
	private int operate(int a, int b, mathop x) {
		return x.aaa(a, b);
	}
}
