package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FrameMenuDemo {
	private JFrame jf;
	private JMenuBar jmenuBar;
	private JMenu jmemu, subMenu;
	private JMenuItem exitItem, subMenuItem;
	
	public void init() {
		jf = new JFrame("listfiles");
		jf.setBounds(300, 100, 600, 500);
		jf.setLayout(new FlowLayout());
		
		jmenuBar = new JMenuBar();
		jmemu = new JMenu("文件");
		subMenu = new JMenu("子菜单");
		subMenuItem = new JMenuItem("子条目");
		exitItem = new JMenuItem("Exit");
		
		jmemu.add(subMenu);
		jmemu.add(exitItem);
		
		subMenu.add(subMenuItem);
		
		// 将菜单添加到菜单条中
		jmenuBar.add(jmemu);
		
		// 将菜单条添加到窗体上
		jf.setJMenuBar(jmenuBar);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setEvent();
		
		jf.setVisible(true);
	}
	
	
	public void setEvent() {
		// 给退出菜单条添加活动监听
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new FrameMenuDemo().init();
	}
}
