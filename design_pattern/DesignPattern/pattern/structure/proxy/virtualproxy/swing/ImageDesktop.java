package virtualproxy.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import virtualproxy.ImageIconProxy;

public class ImageDesktop {
	ImageComponent imageComponent;
	JFrame frame = new JFrame("CD Cover Viewer");
	JMenuBar menuBar;
	JMenu menu;
	Map<String, String> cds = new HashMap<>();
	
	
	public void display() throws Exception {
		cds.put("百度", "https://www.baidu.com/img/bd_logo1.png");
		cds.put("阿里", "http://pic12.nipic.com/20110112/4775714_091244001112_2.jpg");
		cds.put("腾讯", "http://img-arch.pconline.com.cn/images/bbs4/200810/25/1224905129550.jpg");
		
		menuBar = new JMenuBar();
		menu = new JMenu("Favorite CDS");
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		for(String key : cds.keySet()) {
			JMenuItem menuItem = new JMenuItem(key);
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 设置代理Icon，当真正的图片下载完成后，会重绘界面
					imageComponent.setIcon(new ImageIconProxy(getCDUrl(e.getActionCommand())));
					frame.repaint();
				}
			});
		}
		
		URL initialURL = new URL(cds.get("百度"));
		Icon icon = new ImageIconProxy(initialURL);
		// 先配置为虚拟代理，由虚拟代理返回临时的结果，并且此时就启动一个线程去执行下载任务，当下载完成后repaint更新界面
		// ImageNotLoadedState.paintIcon 中，
		imageComponent = new ImageComponent(icon); 
		frame.getContentPane().add(imageComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
		
	}

	protected URL getCDUrl(String name) {
		try {
			return new URL(cds.get(name));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
