package com.asynclife.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.IOUtils;

import com.asynclife.swing.common.MyDialog;
import com.asynclife.swing.common.SystemConst;

public class MenuAndOpenFileDemo extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea_file_content;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuAndOpenFileDemo frame = new MenuAndOpenFileDemo();
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
	public MenuAndOpenFileDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("文件");
		menuBar.add(mnNewMenu);
		
		JMenuItem MenuItem_open = new JMenuItem("打开");
		MenuItem_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFileDialog();
			}
		});
		mnNewMenu.add(MenuItem_open);
		
		JMenuItem MenuItem_exit = new JMenuItem("退出");
		MenuItem_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem MenuItem_save = new JMenuItem("保存");
		MenuItem_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFileDialog();
			}
		});
		mnNewMenu.add(MenuItem_save);
		mnNewMenu.add(MenuItem_exit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea_file_content = new JTextArea();
		scrollPane.setViewportView(textArea_file_content);
	}


	protected void openFileDialog() {
		JFileChooser chooser = new JFileChooser(); // 文件对话框
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);
		
		int retValue = chooser.showOpenDialog(this);
		
		if(retValue == JFileChooser.CANCEL_OPTION) {
			return;
		}
		
		
		if(retValue == JFileChooser.APPROVE_OPTION) {
			// 提示对话框
			MyDialog dialog = new MyDialog();
			dialog.setMessage("You chose to open this file: " +
					chooser.getSelectedFile().getName());
			dialog.setVisible(true);
			
			// 读取文件内容
			
			File file = chooser.getSelectedFile();
			
			List<String> contents = null;
			try {
				contents = IOUtils.readLines(new FileReader(file));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			for(String line : contents) {
				textArea_file_content.append(line);
				textArea_file_content.append(SystemConst.LINE_SEPARATOR);
			}
		}
		
	}
	
	
	protected void saveFileDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(this);
		
		File file = chooser.getSelectedFile();
		
		String content = this.textArea_file_content.getText();
		FileWriter fw = null;
		try {
			 fw = new FileWriter(file, false);
			 IOUtils.write(content, fw);
			 fw.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(fw);
		}
		
	}

}
