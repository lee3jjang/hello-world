package com.tutorial.main;

public class Main {

	public static void main(String[] args) {
		Developer dev = new Developer();
		dev.name = "이상진";
		dev.career = 2;
		dev.type = DevType.WEB;
		
		System.out.println("개발자 이름 : " + dev.name);
		System.out.println("개발자 경력 : " + dev.career);
		System.out.println("직무파트 : " + dev.type);
		System.out.println(dev.type.name());
		System.out.println(dev.type.name().getClass().getSimpleName());
		System.out.println(dev.type.ordinal());
		System.out.println(dev.type.getName());
		
		for(DevType type : DevType.values()) {
			System.out.println(type.getName());
		}
	}
}

