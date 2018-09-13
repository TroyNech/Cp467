package a1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private BufferedImage image;
	private int[] grayScaleValues;
	private int[] computedGrayScales;
	private int width;
	private int height;

	public Image(String fileLocation) {
		try {
			image = ImageIO.read(new File(fileLocation));
			width = image.getWidth();
			height = image.getHeight();
			grayScaleValues = new int[height * width];
			computedGrayScales = new int[height * width];
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image;
	}
	
	public void convoluteImage(float[] operator) {
		storeGrayValues();
		// Next steps to operate on image
		// store computed values in computedGrayScales
		// create new image at the end, traverse through and setRGB();
	}

	private void storeGrayValues() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
			    int p = image.getRGB(x,y);
			    int r = (p>>16)&0xff;
			    int g = (p>>8)&0xff;
			    int b = p&0xff;
			    int grayScaleValue = (r + g + b) / 3;
			    grayScaleValues[y * width + x] = grayScaleValue;
		
			}
	    }
	}
}
