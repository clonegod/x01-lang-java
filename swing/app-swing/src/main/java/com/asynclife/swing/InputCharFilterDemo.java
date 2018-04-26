package com.asynclife.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputCharFilterDemo extends JFrame {

	private JPanel contentPane;
	private JTextField textField_Name;
	private JTextField textField_Name_copy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputCharFilterDemo frame = new InputCharFilterDemo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InputCharFilterDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton firstBtn = new JButton("按我");
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstBtnActionPerformed(e);
			}
		});
		firstBtn.setBounds(170, 119, 93, 23);
		contentPane.add(firstBtn);
		
		JLabel label = new JLabel("姓名");
		label.setBounds(10, 13, 54, 15);
		contentPane.add(label);
		
		textField_Name = new JTextField();
		textField_Name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				textField_Name_KeyTyped(e);
			}
		});
		textField_Name.setBounds(44, 10, 66, 21);
		contentPane.add(textField_Name);
		textField_Name.setColumns(10);
		
		textField_Name_copy = new JTextField();
		textField_Name_copy.setBounds(155, 10, 66, 21);
		contentPane.add(textField_Name_copy);
		textField_Name_copy.setColumns(10);
	}
	
	/**
	 * 过滤键盘录入，只允许数字
	 * @param e
	 */
	protected void textField_Name_KeyTyped(KeyEvent e) {
		char keychar = e.getKeyChar();
		
		if(keychar == KeyEvent.VK_ENTER) {
			firstBtnActionPerformed(null);
		} else {
			if(!(keychar >= '0' && keychar <= '9')) {
				System.err.println("非法字符："+e.getKeyChar());
				e.consume();
			}
		}
		
		
	}

	/**
	 * 按钮事件处理
	 */
	private void firstBtnActionPerformed(ActionEvent e) {
		String input = textField_Name.getText();
		textField_Name_copy.setText(input); // 复制内容到指定文本框
	}
}
