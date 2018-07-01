package exercise;

import java.io.FileOutputStream;
import java.io.IOException;

public class WriteString {
	public static void main(String args[]) throws IOException {
		FileOutputStream fout = new FileOutputStream("C://testout.txt");
		String s = "Welcome to the Jungle!";
		byte b[] = s.getBytes();
		
		fout.write(b);
		fout.close();
		System.out.println(s);
		System.out.println(b);
	}
}
