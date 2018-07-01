package exercise;
import java.io.*;

public class BufferedInput {
	public static void main(String args[]) throws Exception{
		FileInputStream fin = new FileInputStream("C://testout3.txt");
		BufferedInputStream bin = new BufferedInputStream(fin);
		int i;
		while((i=bin.read()) != -1) {
			System.out.print((char)i);
		}
		bin.close();
		fin.close();
	}
}
