package com.asynclife.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;

import com.asynclife.swing.common.SystemConst;

public class ListDirDemo extends JFrame {

	private JPanel contentPane;
	private JTextField textField_input;
	private JScrollPane scrollPane;
	private JTextArea textArea_output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListDirDemo frame = new ListDirDemo();
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
	public ListDirDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textField_input = new JTextField();
		textField_input.setBounds(32, 10, 278, 21);
		textField_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				textField_input_keyPressed(e);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(textField_input);
		textField_input.setColumns(10);
		
		JButton btn = new JButton("转到");
		btn.setBounds(331, 9, 93, 23);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnActionPerformed(e);
			}
		});
		contentPane.add(btn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 61, 392, 178);
		contentPane.add(scrollPane);
		
		textArea_output = new JTextArea();
		scrollPane.setViewportView(textArea_output);
	}

	
	/**
	 * 事件处理
	 * @param e
	 */
	
	protected void textField_input_keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			showDir();
		}
	}

	protected void btnActionPerformed(ActionEvent e) {
		showDir();
	}

	private void showDir() {
		String input = textField_input.getText();
		File dir = new File(input);
		if(dir.exists() && dir.isDirectory()) {
			textArea_output.setText("");
			
			String[] files = dir.list();
			for(String name : files) {
				textArea_output.append(name);
				textArea_output.append(SystemConst.LINE_SEPARATOR);
			}
		}
	}
}
