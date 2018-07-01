package exercise;

import java.io.*;
import java.net.*;

public class MyServer {
	public static void main(String[] args) {
		try {
			
			ServerSocket ss = new ServerSocket(6669);
			System.out.println("===this===");
			Socket s;
			String str;
			DataInputStream dis;
			for(int i=0; i<3; i++) {
				s = ss.accept();
				System.out.println(i+1+" Times");
				dis = new DataInputStream(s.getInputStream());
				str = (String)dis.readUTF();
				System.out.println("Message= " + str);
			}
			ss.close();
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
