package factorymethod;

import factorymethod.factory.ImageReader;
import factorymethod.factory.impl.GifReader;
import factorymethod.factory.impl.JpegReader;
import factorymethod.image.DecodedImage;

public class TestFactoryMethodDemo {
    public static void main(String[] args) {
        String image = args.length == 0 ? "test.jpeg" : args[0];
        String format = image.substring(image.indexOf('.') + 1, (image.length()));
        
        ImageReader reader = null;
        if (format.equals("gif")) {
            reader = new GifReader(image);
        }
        if (format.equals("jpeg")) {
            reader = new JpegReader(image);
        }
        
        assert reader != null;
        
        DecodedImage decodedImage = reader.getDecodeImage();
        System.out.println(decodedImage);
    }
}