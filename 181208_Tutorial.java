package com.sangjin.tutorial;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Tutorial {

	public static void main(String[] args) {
		
		// Runnable AND .run()
		// void ¡æ void
		Runnable r = () -> System.out.println("Hello Functional!");
		r.run();

		// Supplier<T> AND .get()
		// void -> T
		Supplier<String> s = () -> "Hello Supplier";
		System.out.println(s.get());
		
		// Consumer<T> AND .accept()
		// T -> void
		Consumer<String> c = (str) -> System.out.println(str);
		c.accept("Hello Consumer!");
		
		// Function<T,R> AND .apply()
		// T -> R
		Function<String, Integer> f = (str) -> {
			return(Integer.parseInt(str));
		};
		System.out.println("Apply result is... " + f.apply("3"));
		
		// Predicate<T> AND .test()
		// T -> Boolean
		Predicate<String> p = (str) -> {
			return(str.isEmpty());
		};
		System.out.println("Test result is... " + p.test("Hello Predicate!"));
		
		// UnaryOperator<T> AND .apply()
		// T -> T
		UnaryOperator<String> u = (str) -> str + "OPERATOR";
		System.out.println(u.apply("plus "));
		
		// BinaryOperator<T> AND .apply()
		// (T,T) -> T
		BinaryOperator<String> b = (str1, str2) -> str1 + " AND " + str2;
		System.out.println(b.apply("plus", "minus"));
		
		// BiPredicate<T,U> .test()
		// (T,U) -> Boolean
		BiPredicate<String, Integer> bp = (str, num) -> str.equals(Integer.toString(num));
		System.out.println(bp.test("4", 4));
		
		// BiConsumer<T,U> .accept()
		// (T,U) -> void
		BiConsumer<String, Integer> bc = (str, num) -> System.out.println(str + " " + num);
		bc.accept("Hello", 47);
		
		// BiFunction<T,U,R> .apply()
		// (T,U) -> R
		BiFunction<Integer, String, String> bf = (num, str) -> {
			return(String.valueOf(num) + str);
		};
		System.out.println(bf.apply(999, " is good"));
		
	}

}
