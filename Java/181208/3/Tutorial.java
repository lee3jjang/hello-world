package com.sangjin.tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Tutorial {

	public static void main(String[] args) {
		int[] numberArr = {1,2,3,4,5,6};
		List<Integer> numberList = Arrays.asList(1,2,3,4,5,6);
		Set<Integer> numberSet = new HashSet<>(numberList);
		
		Stream.of(1,2,3,4,5,6);
		numberList.stream();
		numberList.stream();
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
		List<Integer> evenList = new ArrayList<>();
		
		for(int num: numbers) {
			if(num % 2 == 0) {
				evenList.add(num);
			}
		}
		System.out.println(evenList);
		
		// iterate
		// start from 1
		// n -> n+2
		// 11 times
		evenList = Stream.iterate(1, n -> n+2)
				.limit(2)
				.filter(num -> num > 0)
				.collect(Collectors.toList());
		
		System.out.println(evenList);
		
		// peek : Consumer
		// filter : T -> Boolean
		Stream<Integer> stream = Stream.iterate(1, n->n+1)
				.limit(6)
				.peek(System.out::println)
				.filter(num -> num % 2 == 0);
		
		List<Integer> lst = stream.collect(Collectors.toList());
		System.out.println(lst);
		
		stream = Stream.of(1,2,3,4,5);
		Stream<Integer> s = stream.peek(System.out::println);
		s.collect(Collectors.toList());
		
		Object obj = null;
		
		boolean b = 1==2 && obj.toString().equals("123");
		System.out.println(b);
		
		List<String> list = Arrays.asList("a", "c", "c");
		
		//.allMatch : s -> Boolean
		boolean b2 = list.stream().allMatch(str -> {
			System.out.println(str);
			return str.equals("a");
		});
		
		System.out.println(b2);
		
		DoubleStream doubleStream = DoubleStream.of(1,2,3,4,5);
		System.out.println(doubleStream.sum());
		Stream<Integer> stream0 = Stream.of(1,2,3,4,5);
		
		System.out.println("----------------------------");
		Stream<Integer> stream1 = Stream.of(1,2,3,4,5);
		List<Integer> l = stream1.map(x -> x*x).collect(Collectors.toList());
		List<Integer> l2 = Stream.iterate(1, n->n+1)
				.limit(10)
				.map(x -> x*x)
				.collect(Collectors.toList());
		System.out.println(l);
		System.out.println(l2);
		
		Stream.iterate(1, n->n+1)
			.limit(10)
			.peek(x -> System.out.println("do it! : " + x))
			// T -> List<T>
			.collect(Collectors.toList());
		
		System.out.println("----------------------------");
		Stream.iterate(1, n->n+1)
			.limit(10)
			.map(x -> x*x)
			// T -> void
			.forEach(x -> System.out.println("Result is... " + x));
		
		System.out.println("----------------------------");
		Optional<Integer> opt = Stream.iterate(1, n->n+1)
				.limit(2)
				.map(x -> x*x)
				.reduce((x,y) -> x+y);
		System.out.println(opt);
		System.out.println(opt.get().getClass());
		
		System.out.println("----------------------------");
		Boolean b0 = Stream.iterate(1, n->n+1)
				.limit(10)
				.map(x -> x*x)
				.anyMatch(x -> x>10);
		System.out.println(b0);
		
		
	}

}




