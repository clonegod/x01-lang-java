package frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class FrameActionDemo {
	private JFrame frame;
	private JButton button;
	private JTextField tf;
	
	/**
	 * 初始化窗体
	 */
	public void init() {
		
		// 初始化窗口，并对窗口的位置与布局进行设定
		frame = new JFrame("my jframe");
		int x = 300, y = 100, width = 600, height = 400;
		frame.setBounds(x, y, width, height);
		frame.setLayout(new FlowLayout());
		
		// 创建文本输入框
		tf = new JTextField();
		tf.setColumns(20);
		frame.add(tf);
		
		// 创建按钮，并添加到窗体中
		button = new JButton("关闭窗口");
		frame.add(button);
		
		setEventListener();
		
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 显示窗体
		frame.setVisible(true);
	}
	
	/**
	 * 用一个方法单独对事件监听进行处理
	 */
	public void setEventListener() {
		// 关闭窗体的事件处理
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// actionPerformed - 不管是鼠标还是键盘所产生的事件，该方法都能得到执行
		button.addActionListener(new ActionListener() {
			// 按钮被点击的事件处理
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		button.addMouseListener(new MouseAdapter() {
			private AtomicInteger ai = new AtomicInteger(0);
			
			// 鼠标进入按钮区域的事件处理 --- 只支持鼠标相关的事件
			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("mouse enter: " + ai.incrementAndGet());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					System.out.println("双击");
				}
			}
		});
		
		button.addActionListener(new ActionListener() {
			// 同时支持鼠标和键盘两种类型的活动状态
			public void actionPerformed(ActionEvent e) {
				System.out.println("actionPerformed ");
			}
		});
		
		tf.addKeyListener(new KeyAdapter() {
			/**
			 * 过滤键盘录入字符:
			 * JTextField - 只有keyTyped()可以实现过滤
			 * TextField - keyPressed()和keyTyped()都可以实现过滤
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				if(String.valueOf(e.getKeyChar()).matches("\\D")) {
					e.consume();
				}
//				if(! Character.isDigit(e.getKeyChar())) {
//					e.consume(); // 取消当前录入的字符
//				}
			}
		});
		
		// 键盘事件的处理- 获取keycode 与 keychar
		button.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(KeyEvent.getKeyText(e.getKeyCode()) + "==" + e.getKeyCode());
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE || 
						e.getKeyCode() == KeyEvent.VK_ENTER ||
						(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C)) {
					System.exit(0);
				}
			}
		});
		
		
	}
	
	public static void main(String[] args) {
		FrameActionDemo demo = new FrameActionDemo();
		demo.init();
	}
}
