package a1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

public class Main {
  public static void main(final String args[]) throws Exception {
	float[] operator = { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f } ;
    Image image = new Image("cat.jpg");
    image.convoluteImage(operator);
  }
}