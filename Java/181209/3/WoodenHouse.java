package com.tutorial;

public class WoodenHouse extends HouseTemplate {
	@Override
	public void buildWalls() {
		System.out.println("Build Wooden Walls");
	}
	
	@Override
	public void buildPillars() {
		System.out.println("Buildind Pillars with Wood coating");
	}
}
