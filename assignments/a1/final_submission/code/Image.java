import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Image {
	private BufferedImage image;
	private String fileName;
	private int[] grayScaleValues;
	private int[] computedGrayScales;
	private int width;
	private int height;
	private int operatorSize = 9;

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
	
	public void convoluteImage(float[] operator, String newFileName) {
		storeGrayValues();
		int[] temp = new int[operatorSize];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int position = y * width + x;
				int centerValue = grayScaleValues[position];
				int fillerValue = 0;
				if (x == width - 1 && y == height - 1) {
					temp[0] = grayScaleValues[(y - 1) * width + x - 1];
					temp[1] = grayScaleValues[(y - 1) * width + x];
					temp[2] = fillerValue;
					temp[3] = grayScaleValues[y * width + x - 1];
					temp[4] = centerValue;
					temp[5] = fillerValue;
					temp[6] = fillerValue;
					temp[7] = fillerValue;
					temp[8] = fillerValue;

				} else if (x == 0 && y == height - 1) {
					temp[0] = fillerValue;
					temp[1] = grayScaleValues[(y - 1) * width + x];
					temp[2] = grayScaleValues[(y - 1) * width + x + 1];
					temp[3] = fillerValue;
					temp[4] = centerValue;
					temp[5] = grayScaleValues[position + 1];
					temp[6] = fillerValue;
					temp[7] = fillerValue;
					temp[8] = fillerValue;
					
				} else if (x == width - 1 && y == 0) {
					temp[0] = fillerValue;
					temp[1] = fillerValue;
					temp[2] = fillerValue;
					temp[3] = grayScaleValues[position - 1];
					temp[4] = centerValue;
					temp[5] = fillerValue;
					temp[6] = grayScaleValues[(y + 1) * width + x - 1];
					temp[7] = grayScaleValues[(y + 1) * width];
					temp[8] = fillerValue;
					
				} else if (x == 0 && y == 0) {
					temp[0] = fillerValue;
					temp[1] = fillerValue;
					temp[2] = fillerValue;
					temp[3] = fillerValue;
					temp[4] = centerValue;
					temp[5] = grayScaleValues[position + 1];
					temp[6] = fillerValue;
					temp[7] = grayScaleValues[(y + 1) * width + x];
					temp[8] = grayScaleValues[(y + 1) * width + x + 1];
					
				} else if (x == 0) {
					temp[0] = fillerValue;
					temp[1] = grayScaleValues[(y - 1) * width + x];
					temp[2] = grayScaleValues[(y - 1) * width + x + 1];
					temp[3] = fillerValue;
					temp[4] = centerValue;
					temp[5] = grayScaleValues[position + 1];
					temp[6] = fillerValue;
					temp[7] = grayScaleValues[(y + 1) * width + x];
					temp[8] = grayScaleValues[(y + 1) * width + x + 1];
				
				} else if (y == height - 1) {
					temp[0] = grayScaleValues[(y - 1) * width + x - 1];
					temp[1] = grayScaleValues[(y - 1) * width + x];
					temp[2] = grayScaleValues[(y - 1) * width + x + 1];
					temp[3] = grayScaleValues[y * width + x - 1];
					temp[4] = centerValue;
					temp[5] = grayScaleValues[y * width + x + 1];
					temp[6] = fillerValue;
					temp[7] = fillerValue;
					temp[8] = fillerValue;
					
				} else if (x == width - 1) {
					temp[0] = grayScaleValues[(y - 1) * width + x - 1];
					temp[1] = grayScaleValues[(y - 1) * width + x];
					temp[2] = fillerValue;
					temp[3] = grayScaleValues[y * width + x - 1];
					temp[4] = centerValue;
					temp[5] = fillerValue;
					temp[6] = grayScaleValues[(y + 1) * width + x - 1];
					temp[7] = grayScaleValues[(y + 1) * width + x];
					temp[8] = fillerValue;
					
				} else if (y == 0) {
					temp[0] = fillerValue;
					temp[1] = fillerValue;
					temp[2] = fillerValue;
					temp[3] = grayScaleValues[y * width + x - 1];
					temp[4] = centerValue;
					temp[5] = grayScaleValues[y * width + x + 1];
					temp[6] = grayScaleValues[(y + 1) * width + x - 1];
					temp[7] = grayScaleValues[(y + 1) * width + x];
					temp[8] = grayScaleValues[(y + 1) * width + x + 1];
					
				} else {
					temp[0] = grayScaleValues[(y - 1) * width + x - 1];
					temp[1] = grayScaleValues[(y - 1) * width + x];
					temp[2] = grayScaleValues[(y - 1) * width + 1];
					temp[3] = grayScaleValues[y * width + x - 1];
					temp[4] = centerValue;
					temp[5] = grayScaleValues[y * width + x + 1];
					temp[6] = grayScaleValues[(y + 1) * width + x - 1];
					temp[7] = grayScaleValues[(y + 1) * width + x];
					temp[8] = grayScaleValues[(y + 1) * width + x + 1];
				}
				
				int sum = 0;
				for (int i = 0; i < operatorSize; i++) {
					sum += temp[i] * operator[i];
				}
				computedGrayScales[position] = sum;
			}
		}
		createNewConvolutedImage(newFileName);
	}
	
	private void createNewConvolutedImage(String newFileName) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int value = computedGrayScales[y * width + x];
				value = value > 255 ?  255 : value;
				value = value < 0 ? 0 : value;
				int rgb = new Color(value, value, value).getRGB();
				newImage.setRGB(x, y, rgb);
			}
	    }
		try {
			ImageIO.write(newImage, "jpg", new File(newFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageJPanel imageJpanel = new ImageJPanel(image, newImage);
		imageJpanel.displayImage();
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