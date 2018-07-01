package exercise;

import java.io.*;

public class BufferedOutput {
	public static void main(String args[]) throws Exception{
		FileOutputStream fout = new FileOutputStream("C://testout3.txt");
		BufferedOutputStream bout = new BufferedOutputStream(fout);
		String s = "Welcome to the City";
		byte b[] = s.getBytes();
		bout.write(b);
		bout.flush();
		bout.close();
		fout.close();		
	}
}
