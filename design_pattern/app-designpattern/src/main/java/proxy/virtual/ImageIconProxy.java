package proxy.virtual;

import java.awt.Component;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
/**
 * 虚拟代理
 *
 */
public class ImageIconProxy implements Icon {
	
	private ImageIcon realIcon;
	
	private String imageName;
	
	private int width;
	
	private int height;
	
	boolean isIconCreated = false;
	
	public ImageIconProxy(String imageName, int width, int height) {
		this.imageName = imageName;
		this.width = width;
		this.height = height;
	}

	public void paintIcon(final Component c, Graphics g, int x, int y) {
		if(isIconCreated) {
			realIcon.paintIcon(c, g, x, y);
			g.drawString("Java and Pattern by xxx", x+20, y+370);
		} else {
			g.drawRect(x, y, width-1, height-1);
			g.drawString("Loading author's phtot", x+20, y+20);
			
			//start another thread to load image
			synchronized (this) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							TimeUnit.SECONDS.sleep(2);
							realIcon = new ImageIcon(imageName);
							isIconCreated = true;
						} catch(Exception e) {
							e.printStackTrace();
						}
						c.repaint();
					}
				});
			}
		}

	}

	public int getIconWidth() {
		return realIcon.getIconWidth();
	}

	public int getIconHeight() {
		return realIcon.getIconHeight();
	}

}
