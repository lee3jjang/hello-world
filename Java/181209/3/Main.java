package com.tutorial;

public class Main {

	public static void main(String[] args) {
		HouseTemplate glassHouse = new GlassHouse();
		HouseTemplate woodenHouse = new WoodenHouse();
		
		glassHouse.buildHouse();
		System.out.println("-----------------------");
		woodenHouse.buildHouse();
	}

}
