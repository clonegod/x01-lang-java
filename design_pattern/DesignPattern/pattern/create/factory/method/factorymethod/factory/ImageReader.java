package factorymethod.factory;

import factorymethod.image.DecodedImage;

public interface ImageReader {
	/**
	 * 工厂方法-将具体对象的创建逻辑推迟到子类中实现
	 */
    DecodedImage getDecodeImage();
}