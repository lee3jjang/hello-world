package com.sangjin.tutorial;

public interface D {
	public String getString();
}

class E implements D {
	@Override
	public String getString() {
		return "Hello World!";
	}
}

class F implements D {
	@Override
	public String getString() {
		return "Hello LichKing";
	}
}

class G {
	D d;
	public void setD(D d) {
		this.d = d;
	}
	
	public String playStrategy() {
		return d.getString();
	}
}