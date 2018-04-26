package factorymethod.factory.impl;

import factorymethod.factory.ImageReader;
import factorymethod.image.DecodedImage;

public class JpegReader implements ImageReader {
    private DecodedImage decodedImage;

    public JpegReader(String image) {
        decodedImage = new DecodedImage(image);
    }

    /**
     * 工厂方法
     */
    @Override
    public DecodedImage getDecodeImage() {
        return decodedImage;
    }
}