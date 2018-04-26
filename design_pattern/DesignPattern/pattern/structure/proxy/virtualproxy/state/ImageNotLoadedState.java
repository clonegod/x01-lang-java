package virtualproxy.state;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import virtualproxy.ImageIconProxy;

public class ImageNotLoadedState implements ImageState {
	
	ImageIconProxy imageIconProxy;
	
	public ImageNotLoadedState(ImageIconProxy imageIconProxy) {
		this.imageIconProxy = imageIconProxy;
	}
	
	Thread retriveThread;
	boolean retriving = false;

	@Override
	public int getIconWidth() {
		return 800;
	}

	@Override
	public int getIconHeight() {
		return 600;
	}
	
	@Override
	public void paintIcon(final Component c, Graphics g, int x, int y) {
		g.drawString("Loading CD cover, please wait...", x+300, y+190);
		if(! retriving) {
			retriving = true;
			retriveThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("Image not exist! Loading: " + imageIconProxy.getUrl().getPath());
						ImageIcon imageIcon = new ImageIcon(imageIconProxy.getUrl(), "CD Cover");
						ImageLoadedState loadedState = imageIconProxy.getImageLoadedState();
						loadedState.setImageIcon(imageIcon);
						// 图片下载完成后，更新Proxy的内部状态
						imageIconProxy.setCurrentState(loadedState);
						c.repaint(); // 重绘-将调用到ImageLoadedState中的paint()
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
			retriveThread.start();
		}
	}

}
