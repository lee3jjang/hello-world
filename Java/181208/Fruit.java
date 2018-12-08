package com.sangjin.tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Fruit {
	private String name;
	private String color;
	
	Fruit(String name, String color){
		this.name = name;
		this.color = color;
	}
	
	String getName() {
		return this.name;
	}
	
	String getColor() {
		return this.color;
	}
	
	static List<Fruit> extractApple(List<Fruit> fruits){
		List<Fruit> resultList = new ArrayList<>();
		for(Fruit fruit: fruits) {
			if("apple".equals(fruit.getName())) {
				resultList.add(fruit);
			}
		}
		return resultList;
	}
	
	static List<Fruit> extractRed(List<Fruit> fruits){
		List<Fruit> resultList = new ArrayList<>();
		for(Fruit fruit: fruits) {
			if("red".equals(fruit.getColor())) {
				resultList.add(fruit);
			}
		}
		return resultList;
	}
	
	
	static List<Fruit> extractFruitList(List<Fruit> fruits, Predicate<Fruit> predicate){
		List<Fruit> resultList = new ArrayList<>();
		for(Fruit fruit: fruits) {
			if(predicate.test(fruit)) {
				resultList.add(fruit);
			}
		}
		return resultList;
	}
	
}


