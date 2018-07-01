package exercise;

import java.io.DataOutputStream;
import java.net.Socket;

public class MyClient {
	public static void main(String[] args) {
		try {
			
			Socket s = new Socket("localhost", 6669);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			dout.writeUTF("Nice to meet you!");
			dout.flush();
			dout.close();
			s.close();
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
