package virtualproxy;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.Icon;

import virtualproxy.state.ImageLoadedState;
import virtualproxy.state.ImageNotLoadedState;
import virtualproxy.state.ImageState;

/**
 * 虚拟代理---提供耗时操作的替代解决方案。
 * 
 * 远程网络加载图片比较耗时，因此建立一个代理对象，当图片尚未下载成功时，显示友好提示。
 * 图片下载完成之后，切换到ImageLoaded状态，重绘图片。
 *
 */
public class ImageIconProxy implements Icon {
	
	URL url;
	
	ImageNotLoadedState noImageState;
	
	ImageLoadedState imageLoadedState;
	
	ImageState currentState;
	
	public ImageIconProxy(URL url) {
		noImageState = new ImageNotLoadedState(this);
		imageLoadedState = new ImageLoadedState(this);
		currentState = noImageState;
		this.url = url;
	}

	@Override
	public int getIconWidth() {
		return currentState.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return currentState.getIconHeight();
	}

	@Override
	public void paintIcon(final Component c, Graphics g, int x, int y) {
		currentState.paintIcon(c, g, x, y);
	}

	
	// getters & setters
	public ImageLoadedState getImageLoadedState() {
		return imageLoadedState;
	}

	public URL getUrl() {
		return url;
	}

	public void setCurrentState(ImageState currentState) {
		this.currentState = currentState;
	}
	
}
