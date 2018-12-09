package com.tutorial.main;

public class Main {

	public static void main(String[] args) {
		Product<String, Integer> prd = new Product<>();
		
		prd.setBrand("DB Inusrance");
		prd.setModelNumber(11700205);
		
		System.out.println(prd.getBrand());
		System.out.println(prd.getModelNumber());
		
		Box<Integer> box = Util.<Integer>boxing(100);
		System.out.println(box.getT());
	}
}

