package com.sangjin.tutorial;

public class Tutorial {

	public static void main(String[] args) {
		
		A a = new A();
		B b = new B();
		C c = new C();
		
		D e = new E();
		D f = new F(); 
		
		G g = new G();
		g.setD(() -> "Hello Lambda Expression");
		
		String str1 = a.getString();
		String str2 = b.getString();
		String str3 = c.getString();
		String str4 = e.getString();
		String str5 = f.getString();
		String str6 = g.playStrategy();
		
		System.out.println(str1);
		System.out.println(str2);
		System.out.println(str3);
		System.out.println(str4);
		System.out.println(str5);
		System.out.println(str6);
		
	}

}




