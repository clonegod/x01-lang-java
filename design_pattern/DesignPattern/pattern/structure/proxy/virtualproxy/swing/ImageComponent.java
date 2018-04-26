package virtualproxy.swing;

import java.awt.Graphics;
import java.awt.HeadlessException;

import javax.swing.Icon;
import javax.swing.JComponent;

public class ImageComponent extends JComponent {
	
	private static final long serialVersionUID = 5119032006362772093L;
	
	private Icon icon;

	public ImageComponent(Icon icon) throws HeadlessException {
		this.icon = icon;
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int x = (800 - w) / 2;
		int y = (600 - h) / 2;
		icon.paintIcon(this, g, x, y);
	}
}
