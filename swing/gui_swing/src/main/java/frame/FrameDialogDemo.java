package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrameDialogDemo {
	private JFrame jf;
	private JTextField jtf;
	private JButton jbtn;
	private JScrollPane jsp;
	private JTextArea jta;
	
	private JDialog jd;
	private JLabel jl;
	private JButton okBtn;
	
	public void init() {
		jf = new JFrame("listfiles");
		jf.setBounds(300, 100, 600, 500);
		jf.setLayout(new FlowLayout());
		
		jtf = new JTextField();
		jtf.setColumns(30);
		
		jbtn = new JButton("执行");

		jta = new JTextArea(25, 50);
		jsp = new JScrollPane(jta);
		
		jf.add(jtf);
		jf.add(jbtn);
		jf.add(jsp);
		
		okBtn = new JButton("确定");
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addEventListener();
		
		jf.setVisible(true);
	}
	
	private void addEventListener() {
		// 列出文件事件监听-按钮
		jbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listFileByInputPath();
			}
		});
		
		// 监听键盘录入事件
		jtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					// jbtn.doClick();
					listFileByInputPath();
				}
			}
		});
		
		// 确定按钮
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jd.setVisible(false);
			}
		});
		
	}
	
	private void listFileByInputPath() {
		jta.setText(""); // 清空文本区
		
		String path = jtf.getText(); // 获取输入框中的路径
		
		File dir = new File(path);
		if(dir.exists() && dir.isDirectory()) {
			String[] files = dir.list();
			for(String str : files) {
				jta.append(str);
				jta.append(System.lineSeparator());
			}
		} else {
			jd = new JDialog(jf, "我的对话框", true);
			jd.setBounds(400, 200, 400, 100);
			jd.setLayout(new FlowLayout());
			jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			jl = new JLabel("目录"+path+"不存在");
			jd.add(jl);
			jd.add(okBtn);
			jd.setVisible(true);
		}
	}

	public static void main(String[] args) {
		FrameDialogDemo demo = new FrameDialogDemo();
		demo.init();
	}
}
