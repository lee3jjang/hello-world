package exercise;
import java.io.*;


public class ReadChar {
	public static void main(String args[]) throws IOException {
		FileInputStream fin = new FileInputStream("C://testout2.txt");
		int i;

		while((i=fin.read()) != -1){
			System.out.println((char)i);
			System.out.println(i);
		}
		
		fin.close();
	}
}
