package frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 可执行jar的制作步骤：
 * 	cd E:\practice\gui_swing\target\classes
 * 
 * 在classes目录下创建manifest.txt文件，内容如下：
 * 	Main-Class: frame.FrameFileDialogDemo
 * 注意：上面的行尾要回车换行。
 * 
 * 执行打包命令，其中包含指定配置清单的参数：
 * E:\practice\gui_swing\target\classes>jar -cvfm demo.jar manifest.txt frame/
 * 
 * 最后，生成jar文件。
 * 
 * 
 */
public class FrameFileDialogDemo {
	private JFrame jf;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem, saveItem, exitItem;
	
	private FileDialog openDialog, saveDialog;
	
	private JTextArea jta;
	
	private File file;

	public FrameFileDialogDemo() {
		init();
	}

	private void init() {
		jf = new JFrame("my window");
		jf.setBounds(300, 100, 650, 600);
		jf.setLayout(new BorderLayout());
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("文件");
		
		openItem = new JMenuItem("打开");
		saveItem = new JMenuItem("保存");
		exitItem = new JMenuItem("退出");
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		
		jf.setJMenuBar(menuBar);
		
		jta = new JTextArea();
		jf.add(jta);
		
		openDialog = new FileDialog(jf, "打开文件", FileDialog.LOAD); // 打开文件模式 
		saveDialog = new FileDialog(jf, "保存文件", FileDialog.SAVE); // 保存文件模式
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addMenuItemEventListener();
		
		jf.setVisible(true);
		
	}
	
	
	public void addMenuItemEventListener() {
		this.exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDialog.setVisible(true);
				String dir = openDialog.getDirectory();
				String filename = openDialog.getFile();
				if(dir == null || filename == null) {
					System.out.println("dir or filename is null");
					return;
				} 
				String filepath = dir + "/" + filename;
				file = new File(filepath);
				try {
					List<String> lines = IOUtils.readLines(new FileInputStream(file), "GBK");
					jta.setText("");
					for(String line : lines) {
						jta.append(line);
						jta.append(System.lineSeparator());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		this.saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 如果文件已经存在，则不显示保存对话框，直接保存数据到当前file对象！
				if(file == null) {
					saveDialog.setVisible(true);
					String dir = saveDialog.getDirectory();
					String filename = saveDialog.getFile();
					if(dir == null || filename == null) {
						System.out.println("dir or filename is null");
						return;
					}
					String filepath = dir + "/" + filename;
					file = new File(filepath);
				}
				try {
					FileUtils.write(file, jta.getText(), "GBK");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new FrameFileDialogDemo();
	}
}
