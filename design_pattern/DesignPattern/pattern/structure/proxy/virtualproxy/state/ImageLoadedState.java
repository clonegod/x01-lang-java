package virtualproxy.state;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import virtualproxy.ImageIconProxy;

public class ImageLoadedState implements ImageState {
	
	ImageIconProxy imageIconProxy;
	
	public ImageLoadedState(ImageIconProxy imageIconProxy) {
		this.imageIconProxy = imageIconProxy;
	}

	ImageIcon imageIcon;
	
	@Override
	public void paintIcon(final Component c, Graphics g, int x, int y) {
		imageIcon.paintIcon(c, g, x, y);
	}

	@Override
	public int getIconWidth() {
		return imageIcon.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return imageIcon.getIconHeight();
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
	
}
