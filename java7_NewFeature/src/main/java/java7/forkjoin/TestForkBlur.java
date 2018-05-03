package java7.forkjoin;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TestForkBlur {
	
    // Plumbing follows.
    public static void main(String[] args) throws Exception {
    	
        String srcName = "res/red-tulips.jpg";
        File srcFile = new File(srcName);
        BufferedImage image = ImageIO.read(srcFile);
        
        System.out.println("Source image: " + srcName);
        
//        ForkBlur.sThreshold = Integer.MAX_VALUE;
        BufferedImage blurredImage = ForkBlur.blur(image);
        
        String dstName = "res/blurred-tulips.jpg";
        File dstFile = new File(dstName);
        ImageIO.write(blurredImage, "jpg", dstFile);
        
        System.out.println("Output image: " + dstName);
        
    }
	
	
}
