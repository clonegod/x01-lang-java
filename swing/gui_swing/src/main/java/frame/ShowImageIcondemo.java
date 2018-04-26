package frame;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowImageIcondemo {
	public static void main(String[] args) throws Exception {
		test();
	}
	static void test() throws Exception {
		JFrame frame = new JFrame("Vitural Proxy Demo");
		frame.setLayout(new BorderLayout());
		frame.setBounds(300, 100, 600, 600);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		URL url = new URL("https://www.baidu.com/img/bd_logo1.png");
		Icon icon = new ImageIcon(url); 
		
		JLabel jlabel = new JLabel("", icon, JLabel.CENTER);
		
		frame.getContentPane().add(jlabel);
		frame.setVisible(true);
	}
}
