package com.tutorial.main;

public enum DevType {
	MOBILE("안드로이드"), WEB("스프링"), SERVER("리눅스");
	
	final private String name;

	public String getName() {
		return name;
	}

	private DevType(String name) {
		this.name = name;
	}
}
