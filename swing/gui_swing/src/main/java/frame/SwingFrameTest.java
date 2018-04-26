package frame;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class SwingFrameTest {
	
	/**
	 * 创建窗体
	 * 	frame默认使用边界布局，如果没有指定东南西北中，则默认使用居中方式，并且填充满整个窗体！
	 */
	//@Test
	public void test01_CreateFrame() throws Exception {
		Frame frame = new Frame("我的窗口");
		frame.setLocation(300, 300); // 窗口在屏幕的位置 
		frame.setSize(400, 300); // 窗口大小
		
		frame.setLayout(new FlowLayout()); // 设置窗口的布局方式(frame默认采用的BoderLayout)
 		
		// 创建一张图片，添加到Label中，再将Label添加到窗体中。
		URL url = new URL("http://reg.email.163.com/unireg/call.do?cmd=register.verifyCode&v=common/verifycode/vc_en&vt=mobile_acode&t=1466175583520");
		ImageIcon imageIcon = new ImageIcon(url);
		JLabel label = new JLabel(imageIcon);
		frame.add(label);
		
		
		// 创建1个按钮，添加到窗体中
		JButton btn = new JButton("我是一个按钮");
		frame.add(btn);
		
		frame.setVisible(true);
		
		Thread.sleep(3000);
	}
	
	/**
	 * 事件监听
	 * 	事件源 - 发生事件的源对象
	 * 	特定事件 - 点击事件，双击事件，键盘事件 等
	 * 	事件响应处理 - 对具体发生的事件进行响应或者进入任务处理
	 */
	//@Test
	public void testEventListener() throws Exception {
		Frame frame = new Frame("我的窗口");
		frame.setLocation(300, 300); // 窗口在屏幕的位置 
		frame.setSize(400, 300); // 窗口大小
		
		frame.setLayout(new FlowLayout()); // 设置窗口的布局方式(frame默认采用的BoderLayout)
		
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		} );
		
		frame.setVisible(true);
		
		Thread.currentThread().join();
	}
	
	
	
}
