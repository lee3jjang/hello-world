package esg;
import java.awt.*;
import java.awt.event.*;

public class Lambda_expr_exer_1 {
	public static void main(String args[]) {
		Frame frame = new Frame("Actional Listener");
		
		Button b = new Button("Click Here");
		b.setBounds(50,100,80,50);
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello World!");
			}
		});
		
		b.addActionListener(e -> System.out.println("Hello World!"));
		
		frame.add(b);
		frame.setSize(200,200);
		frame.setLayout(null);
		frame.setVisible(true);
	}
}
