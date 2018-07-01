package esg;

import java.util.Arrays;

@FunctionalInterface
interface Func {
	public int calc(int a, int b);
}

public class LambdaExpr {
	public static void main(String args[]) {
		
		//// Lambda Expression ////
		Func add = (int a, int b) -> a+b;
		int result = add.calc(100, -46);
		System.out.println(result);
		
		//// Stream API ////
		// forEach
		Arrays.asList(1,2,3).stream().forEach(System.out::println);
		
		// map
		Arrays.asList(1,2,3,-2).stream().map(i -> i*i).forEach(System.out::println);
		
		// limit
		Arrays.asList(-20,-30,-40).stream().limit(2).forEach(System.out::println);
		
		// skip
		Arrays.asList(-20,-30,-40).stream().skip(1).forEach(System.out::println);
		
		// filter
		Arrays.asList(100,200,300).stream().filter(x ->  x <= 200).forEach(System.out::println);
		
		// flatMap
		Arrays.asList(Arrays.asList(1,2), Arrays.asList(10,20,30), Arrays.asList(-1,-2,-3,-4)).stream()
			.flatMap(x -> x.stream())
			.forEach(System.out::println);
		
		// reduce
		int x = Arrays.asList(99,88,77).stream()
			.reduce((a,b) -> a-b)
			.get();
		System.out.println(x);
		
		// collection
		Arrays.asList(1,2,3).stream()
			.iterator();
	}

}
