package com.tutorial.main;

public class Main {

	public static void main(String[] args) {
		Developer dev = new Developer();
		dev.name = "�̻���";
		dev.career = 2;
		dev.type = DevType.WEB;
		
		System.out.println("������ �̸� : " + dev.name);
		System.out.println("������ ��� : " + dev.career);
		System.out.println("������Ʈ : " + dev.type);
		System.out.println(dev.type.name());
		System.out.println(dev.type.name().getClass().getSimpleName());
		System.out.println(dev.type.ordinal());
		System.out.println(dev.type.getName());
		
		for(DevType type : DevType.values()) {
			System.out.println(type.getName());
		}
	}
}

