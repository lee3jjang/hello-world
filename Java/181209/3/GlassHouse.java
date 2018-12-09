package com.tutorial;

public class GlassHouse extends HouseTemplate {
	@Override
	public void buildWalls() {
		System.out.println("Build Glass Walls");
	}
	
	@Override
	public void buildPillars() {
		System.out.println("Buildind Pillars with glass coating");
	}

}
