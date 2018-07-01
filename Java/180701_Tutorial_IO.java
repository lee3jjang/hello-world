package exercise;
import java.io.*;

public class Tutorial_IO {
	public static void main(String args[]) throws IOException {
	System.out.println("This is a simple message");
	System.err.println("This is a simple error message");
	
	int i = System.in.read();
	System.out.println("The char entered is : " + (char)i);
	}
}