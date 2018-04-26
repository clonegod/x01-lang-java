package com.asynclife.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App extends JFrame {
	
	
	private JButton north;
	private JButton south;
	private JButton west;
	private JButton east;
	private JButton center;
	
	
	public App() {
		init();
	}

	private void init() {
		this.setTitle("App");
		this.setBounds(300, 100, 800, 600);
		this.setBackground(Color.GREEN);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		this.setLayout(new BorderLayout(20, 5)); // 边界布局 - Frame默认的布局管理器：上下左右中，添加时需指定其边界属性。如果没设置设置的边界，则默认居中且平铺
		// this.setLayout(new FlowLayout()); // 流式布局 - Pannel默认的布局管理器，从走到右依次排列
		// this.setLayout(new GridLayout()); // 网格布局
		// this.setLayout(new CardLayout()); // 卡片布局 - 选项卡
		// this.setLayout(new GridBagLayout()); // 不规则矩阵布局
		// 窗体中先添加不同的面板，再将组件放入到Pannel中
		
		// 创建组件
		north = new JButton("上北");
		south = new JButton("下南");
		west = new JButton("左西");
		east = new JButton("右东");
		center = new JButton("中间");
		
		
		// 将组件添加到窗体中，并指定边界布局
		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		this.add(west, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);
		this.add(center, BorderLayout.CENTER);
		
		
		/**
		 * 事件监听机制：
		 * 1. 事件源-组件 ：鼠标、键盘、按钮
		 * 2. 事件类型 - Event 各种事件：按钮被点击、键盘按键被按下
		 * 3. 监听器 - Listener 注册事件到事件源
		 * 4. 事件处理 - Event Handler 预先定义好具体事件发生时的响应动作
		 * 
		 */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("窗口被关闭");
				System.exit(DISPOSE_ON_CLOSE);
			}
			
		});
	}
	
	public static void main(String[] args) {
		new App();
	}
}
