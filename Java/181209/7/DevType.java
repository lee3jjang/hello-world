package com.tutorial.main;

public enum DevType {
	MOBILE("�ȵ���̵�"), WEB("������"), SERVER("������");
	
	final private String name;

	public String getName() {
		return name;
	}

	private DevType(String name) {
		this.name = name;
	}
}
