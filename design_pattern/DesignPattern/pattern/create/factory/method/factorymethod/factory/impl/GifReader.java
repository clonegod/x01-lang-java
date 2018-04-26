package factorymethod.factory.impl;

import factorymethod.factory.ImageReader;
import factorymethod.image.DecodedImage;

public class GifReader implements ImageReader {
    private DecodedImage decodedImage;

    public GifReader(String image) {
        this.decodedImage = new DecodedImage(image);
    }

    /**
     * 工厂方法
     */
    @Override
    public DecodedImage getDecodeImage() {
        return decodedImage;
    }
}