package com.sangjin.tutorial;

public class A {
	public String getString() {
		return "Hello World";
	}
}

class B extends A {
	
}

class C {
	public String getString() {
		A a = new A();
		return a.getString();
	}
}