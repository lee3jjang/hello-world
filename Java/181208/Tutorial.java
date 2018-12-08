package com.sangjin.tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Tutorial {

	public static void main(String[] args) {
		
		Movable mov1 = new Movable() {
			@Override
			public void move(String str) {
				System.out.println("gogo move move " + str);
			}
		};
		Movable mov2 = (str) -> System.out.println("gogo move move " + str);
		Movable car = new Car();
		
		mov1.move("TAXI1");
		mov2.move("TAXI2");
		car.move("CAR1");
		
		List<Fruit> fruits = Arrays.asList(new Fruit("apple", "red"), new Fruit("melon", "green"), new Fruit("banana", "yellow"));
		
		System.out.println(Fruit.extractRed(fruits));
		System.out.println(Fruit.extractApple(fruits));
		
		List<Fruit> melonList = Fruit.extractFruitList(fruits, new Predicate<Fruit>() {
			@Override
			public boolean test(Fruit fruit) {
				return "melon".equals(fruit.getName());
			}
		});
		List<Fruit> greenList = Fruit.extractFruitList(fruits, (fruit) -> "green".equals(fruit.getColor()));
		
		System.out.println(melonList);
		System.out.println(greenList);
		
	}

}


