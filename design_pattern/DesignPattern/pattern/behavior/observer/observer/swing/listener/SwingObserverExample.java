package observer.swing.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SwingObserverExample {
	JFrame frame; // 窗口

	public static void main(String[] args) {
		SwingObserverExample example = new SwingObserverExample();
		example.go();
	}

	public void go() {
		frame = new JFrame();
		JButton button = new JButton("Should I do it?");
		
		/**
		 * 注册观察者
		 * 	JButton的父类AbstractButton中的addActionListener(ActionListener l)方法
		 */
		button.addActionListener(new AngelListener());
		button.addActionListener(new DevilListener());
		
		frame.getContentPane().add(BorderLayout.CENTER, button);
		
		// 在这里设置frame属性
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 300, 300, 300);
		frame.setVisible(true);
	}

	class AngelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Angel say: Don't do it, you might regret it!");
		}
	}

	class DevilListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Devil say: Come on, do it!");
		}
	}
}