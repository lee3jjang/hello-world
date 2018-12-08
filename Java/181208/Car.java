package com.sangjin.tutorial;

public class Car implements Movable{
	@Override
	public void move(String str) {
		System.out.println("gogo car move " + str);
	}
}
