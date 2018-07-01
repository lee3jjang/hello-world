package exercise;

import java.io.*;

public class WriteByte {
	public static void main(String args[]) throws IOException {
		try {
			FileOutputStream fout = new FileOutputStream("C://testout.txt");
			fout.write(65);
			fout.close();
			System.out.println("Success...");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
